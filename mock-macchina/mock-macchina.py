from numpy.random import default_rng
import requests
import time

# Configurazione
# media e varianza sono calcolati dai limiti, assumendo che:
#  - la media è a metà tra i limiti
#  - i limiti sono distanti dalla media di 3 * sigma 
url = "http://api:8081/detections"
machine = 1
characteristic = "frequenza_di_oscillazione_pistone"
limite_min = 100
limite_max = 300

# Codice
rng = default_rng()
mu = (limite_max + limite_min) / 2
sigma = (limite_max - limite_min) / 6
time.sleep(3) # Evita di crashare, anche se c'è il restart automatico
while True:
    time.sleep(1)
    value = rng.normal(mu, sigma)
    json = {
        'macchina': machine,
        'caratteristica': characteristic,
        'value': value
    }
    requests.post(url, json = json)
