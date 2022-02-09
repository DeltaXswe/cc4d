from numpy.random import default_rng
import requests
import time

# Configurazione
# media e varianza sono calcolati da "min" e "max", assumendo che:
#  - la media è a metà tra i limiti
#  - i limiti sono distanti dalla media di 3 * sigma 
url = "http://api:8081/detections"
machine_characteristics = [
    { "machine": 1, "characteristic": "frequenza_di_oscillazione_pistone", "min": 100, "max": 300 },
    { "machine": 1, "characteristic": "temperatura_dell_acqua", "min": 100, "max": 300 },
    { "machine": 2, "characteristic": "frequenza_di_produzione_tessuto", "min": 0, "max": 10 }
]

# Codice
rng = default_rng()
time.sleep(3) # Evita di crashare, anche se c'è il restart automatico
while True:
    time.sleep(1)
    for mc in machine_characteristics:
        mu = (mc["max"] + mc["min"]) / 2
        sigma = (mc["max"] - mc["min"]) / 6
        value = rng.normal(mu, sigma)
        json = {
            'machine': mc["machine"],
            'characteristic': mc["characteristic"],
            'value': value
        }
        requests.post(url, json = json)
