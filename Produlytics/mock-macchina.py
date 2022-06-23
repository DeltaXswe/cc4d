from numpy.random import default_rng
import requests
import time
import threading

url = "http://localhost:81/detections"
timestep = 1

def generate_detections(min, max, type):
  rng = default_rng()
  mu = (max + min) / 2
  sigma = (max - min) / 6
  
  def normals(n = None):
    n = n if n != None else 5 if type == "outliers" else 20
    for i in range(n):
      yield mu if type == "outliers" else rng.normal(mu, sigma)
  
  def beyond_limits():
    yield max + sigma
    yield from normals(1)
    yield min - sigma
    
  def mixture():
    for i in range(8):
      base = rng.choice([min, max - 1.8 * sigma])
      yield rng.uniform(base, base + 1.8 * sigma)
  
  def over_control():
    value = min
    for i in range(7):
      value = rng.uniform(value, max)
      yield value
      value = rng.uniform(min, value)
      yield value
      
  def stratification():
    for i in range(15):
      yield rng.uniform(mu - sigma, mu + sigma)
      
  def trend():
    for i in range(7):
      start = min + sigma / 2 + i / 6 * 5 * sigma
      yield rng.uniform(start - sigma / 1.9, start + sigma / 1.9)
    
  def zone_A():
    base = rng.choice([min, max - 0.8 * sigma])
    for i in range(2):
      yield rng.uniform(base, base + 0.8 * sigma)
    
  def zone_B():
    base = rng.choice([min, max - 1.8 * sigma])
    for i in range(4):
      yield rng.uniform(base, base + 1.8 * sigma)
    
  def zone_C():
    sign = rng.choice([-1, 1])
    for i in range(7):
      yield mu + sign * abs(rng.normal(mu, sigma) - mu + sigma / 2)
  
  if type == "real":
    while True:
      yield from normals(1)
  elif type == "semireal" or type == "outliers":
    while True:
      yield from normals(10)
      yield from beyond_limits()
      
      yield from normals()
      yield from mixture()
        
      yield from normals()
      yield from over_control()
      
      yield from normals()
      yield from stratification()
      
      yield from normals()
      yield from trend()
      
      yield from normals()
      yield from zone_A()
      
      yield from normals()
      yield from zone_B()
      
      yield from normals()
      yield from zone_C()
      
      yield from normals()
      yield from normals()
      yield from normals()
      yield from normals()
  
def mock_daemon(api_key, characteristic_name, min, max, type):
  for value in generate_detections(min, max, type):
    time.sleep(timestep)
    json = {
      'apiKey': api_key,
      'characteristic': characteristic_name,
      'value': value
    }
    requests.post(url, json = json)

while True:
  print()
  api_key = input("Chiave API: ")
  characteristic_name = input("Nome caratteristica: ")
  min = int(input("Limite inferiore: "))
  max = int(input("Limite superiore: "))
  type = input("Tipo (real, semireal, outliers): ")
  print("Started!")
  
  thread = threading.Thread(
    target = mock_daemon,
    args = [api_key, characteristic_name, min, max, type],
    daemon = True
  ).start()
