from numpy.random import default_rng
import requests
import time

api_key = input("Chiave API: ")
characteristic_name = input("Nome caratteristica: ")
min = int(input("Limite inferiore: "))
max = int(input("Limite superiore: "))

url = "http://localhost:81/detections"

# Codice
rng = default_rng()
while True:
    time.sleep(1)
    mu = (max + min) / 2
    sigma = (max - min) / 6
    value = rng.normal(mu, sigma)
    json = {
        'apiKey': api_key,
        'characteristic': characteristic_name,
        'value': value
    }
    requests.post(url, json = json)
