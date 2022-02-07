FROM python:3

RUN pip install --no-cache requests numpy

WORKDIR /mock-macchina
COPY . .

CMD ["python", "./mock-macchina.py"]
