package it.deltax.produlytics.api.unit.detections.business.domain.queue;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueueImpl;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerie;
import it.deltax.produlytics.api.detections.business.domain.serie.DetectionSerieFactory;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class DetectionQueueTest {
  @Test
  @Timeout(value = 3)
  void testNormal() {
    CharacteristicId characteristicId1 = new CharacteristicId(1, 1);
    CharacteristicId characteristicId2 = new CharacteristicId(1, 2);

    Detection detection1a = new Detection(characteristicId1, Instant.now(), 1);
    Detection detection1b = new Detection(characteristicId1, Instant.now(), 2);
    Detection detection2a = new Detection(characteristicId2, Instant.now(), 3);

    AtomicBoolean characteristic1Created = new AtomicBoolean(false);
    AtomicBoolean characteristic2Created = new AtomicBoolean(false);

    final int[] seen = {0, 0};

    DetectionSerieFactory factory =
        characteristicId -> {
          // Controlla che la queue non vada in timeout troppo presto.
          if (characteristicId == characteristicId1) {
            boolean alreadyCreated = characteristic1Created.getAndSet(true);
            assert !alreadyCreated;
          } else if (characteristicId == characteristicId2) {
            boolean alreadyCreated = characteristic2Created.getAndSet(true);
            assert !alreadyCreated;
          } else {
            // Controlla che la queue non crei nuove caratteristiche.
            throw new RuntimeException("La Serie Factory ha ricevuto un id non corretto");
          }

          AtomicBoolean inserting = new AtomicBoolean(false);

          return (DetectionSerie)
              rawDetection -> {
                // Controlla che una rilevazione venga processata solo dopo quella precedente
                boolean alreadyInserting = inserting.getAndSet(true);
                assert !alreadyInserting;

                // Controlla che la rilevazione ottenuta sia quella corretta
                if (characteristicId == characteristicId1) {
                  if (seen[0] == 0) {
                    assert rawDetection == detection1a;
                  } else if (seen[0] == 1) {
                    assert rawDetection == detection1b;
                  } else {
                    throw new RuntimeException("La Serie ha ricevuto una rilevazione inesistente");
                  }
                  seen[0] += 1;
                } else {
                  if (seen[1] == 0) {
                    assert rawDetection == detection2a;
                  } else {
                    throw new RuntimeException("La Serie ha ricevuto una rilevazione inesistente");
                  }
                  seen[1] += 1;
                }

                // Simula il processo della rilevazione
                try {
                  Thread.sleep(100);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }

                inserting.set(false);
              };
        };

    DetectionQueueImpl queue = new DetectionQueueImpl(factory);

    queue.enqueueDetection(detection1a);
    queue.enqueueDetection(detection2a);
    queue.enqueueDetection(detection1b);

    queue.close();

    assert characteristic1Created.get();
    assert characteristic2Created.get();

    assert seen[0] == 2;
    assert seen[1] == 1;
  }
}
