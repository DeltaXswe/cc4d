package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ControlLimitsCalculatorTest {
  @Test
  void testMeanStddevOnly() {
    CharacteristicId findCharacteristicId = new CharacteristicId(42, 69);
    FindLimitsPort findLimitsPort =
        characteristicId -> {
          assert characteristicId == findCharacteristicId;
          Optional<MeanStddev> meanStddev = Optional.of(new MeanStddev(30, 10));
          Optional<TechnicalLimits> technicalLimits = Optional.empty();
          return new LimitsInfo(technicalLimits, meanStddev);
        };
    ControlLimitsCalculator calculator = new ControlLimitsCalculatorImpl(findLimitsPort);
    assert calculator.calculateControlLimits(findCharacteristicId).equals(new ControlLimits(0, 60));
  }

  @Test
  void testTechnicalLimitsOnly() {
    CharacteristicId findCharacteristicId = new CharacteristicId(42, 69);
    FindLimitsPort findLimitsPort =
        characteristicId -> {
          assert characteristicId == findCharacteristicId;
          Optional<MeanStddev> meanStddev = Optional.empty();
          Optional<TechnicalLimits> technicalLimits = Optional.of(new TechnicalLimits(10, 100));
          return new LimitsInfo(technicalLimits, meanStddev);
        };
    ControlLimitsCalculator calculator = new ControlLimitsCalculatorImpl(findLimitsPort);
    assert calculator
        .calculateControlLimits(findCharacteristicId)
        .equals(new ControlLimits(10, 100));
  }

  @Test
  void testBoth() {
    CharacteristicId findCharacteristicId = new CharacteristicId(42, 69);
    FindLimitsPort findLimitsPort =
        characteristicId -> {
          assert characteristicId == findCharacteristicId;
          Optional<MeanStddev> meanStddev = Optional.of(new MeanStddev(30, 10));
          Optional<TechnicalLimits> technicalLimits = Optional.of(new TechnicalLimits(10, 100));
          return new LimitsInfo(technicalLimits, meanStddev);
        };
    ControlLimitsCalculator calculator = new ControlLimitsCalculatorImpl(findLimitsPort);
    assert calculator.calculateControlLimits(findCharacteristicId).equals(new ControlLimits(0, 60));
  }

  @Test
  void testNone() {
    CharacteristicId findCharacteristicId = new CharacteristicId(42, 69);
    FindLimitsPort findLimitsPort =
        characteristicId -> {
          assert characteristicId == findCharacteristicId;
          Optional<MeanStddev> meanStddev = Optional.empty();
          Optional<TechnicalLimits> technicalLimits = Optional.empty();
          return new LimitsInfo(technicalLimits, meanStddev);
        };
    ControlLimitsCalculator calculator = new ControlLimitsCalculatorImpl(findLimitsPort);
    assertThrows(
        RuntimeException.class, () -> calculator.calculateControlLimits(findCharacteristicId));
  }
}
