from flask import Flask, jsonify

app = Flask(__name__)

pera_id = "6f6a3a43-1d28-4c54-84d9-912b6d893c98"
jovan_id = "7f7a3a43-1d28-4c54-84d9-912b6d893c98"
marko_id = "8f8a3a43-1d28-4c54-84d9-912b6d893c98"


@app.route('/pera-data', methods=['GET'])
def pera_data():
    pera = {
        "id": pera_id,
        "name": "Pera Peric",
        "weight": 100,
        "conscious": True,
        "pO2": 7.9,
        "pCO2": 7.1,
        "participationPercentage": 100.0,
        "tidalVolume": 300.0,
        "frequency": 36,
        "minuteVolume": 10800.0,
        "pressure": 3.0,
        "resistance": 0.245,
        "gasFlow": 6.0,
        "compliance": 4.0,
        "FiO2": 30.0,
        "respiratorMode": None,
        "volumeControlled": True
    }
    return jsonify(pera)


@app.route('/jovan-data', methods=['GET'])
def jovan_data():
    jovan = {
        "id": jovan_id,
        "name": "Jovan Jovic",
        "weight": 95,
        "conscious": True,
        "pO2": 7.9,
        "pCO2": 7.1,
        "participationPercentage": 75.0,
        "tidalVolume": 290.0,
        "frequency": 38,
        "minuteVolume": 1120.0,
        "pressure": 3.0,
        "resistance": 0.245,
        "gasFlow": 6.0,
        "compliance": 4.0,
        "FiO2": 25.0,
        "respiratorMode": None,
        "volumeControlled": True
    }
    return jsonify(jovan)


@app.route('/marko-data', methods=['GET'])
def marko_data():
    marko = {
        "id": marko_id,
        "name": "Marko Markovic",
        "weight": 90,
        "conscious": True,
        "pO2": 7.9,
        "pCO2": 7.1,
        "participationPercentage": 70.0,
        "tidalVolume": 350.0,
        "frequency": 40,
        "minuteVolume": 14000.0,
        "pressure": 3.0,
        "resistance": 0.245,
        "gasFlow": 6.0,
        "compliance": 4.0,
        "FiO2": 30.0,
        "respiratorMode": None,
        "volumeControlled": True
    }
    return jsonify(marko)


@app.route('/get-worse/<name>')
def get_worse(name):
    patient_id = ""
    if name == "pera":
        patient_id = pera_id
    elif name == "jovan":
        patient_id = jovan_id
    elif name == "marko":
        patient_id = marko_id
    change_event = {
        "patientId": patient_id,
        "deltaPO2": -0.4,
        "deltaPCO2": 0.3,
        "deltaParticipationPercentage": -7.5
    }
    return jsonify(change_event)


@app.route('/get-better/<name>')
def get_better(name):
    patient_id = ""
    if name == "pera":
        patient_id = pera_id
    elif name == "jovan":
        patient_id = jovan_id
    elif name == "marko":
        patient_id = marko_id
    change_event = {
        "patientId": patient_id,
        "deltaPO2": 0.4,
        "deltaPCO2": -0.3,
        "deltaParticipationPercentage": 7.5
    }
    return jsonify(change_event)


@app.route('/inhale-event/<name>')
def inhale_evevent(name):
    patient_id = ""
    if name == "pera":
        patient_id = pera_id
    elif name == "jovan":
        patient_id = jovan_id
    elif name == "marko":
        patient_id = marko_id
    inhalation_event = {
        "patientId": patient_id,
        "inhaledVolume": 500
    }
    return jsonify(inhalation_event)


if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=False)
