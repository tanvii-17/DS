from flask import Flask, render_template, request

app = Flask(__name__)

@app.route('/')
def home():
    return render_template('index.html')


# Calculator web service
@app.route('/calculate', methods=['POST'])
def calculate():
    num1 = float(request.form['num1'])
    num2 = float(request.form['num2'])
    operation = str(request.form["operation"])
    result = None
    if(operation=="add"):
        result = num1 + num2
    elif(operation=="subtract"):
        result = num1 - num2
    elif(operation=="multiply"):
        result = num1 * num2
    elif(operation=="divide"):
        result = num1 / num2
    return f'The Answer is: {result}'


# Simple Interest calculator
# @app.route('/simpleinterest', methods=['POST'])
# def simpleinterest():
#     principal = float(request.form['principal'])
#     rate = float(request.form['rate'])
#     time = int(request.form["time"])
#     interest = (principal * rate * time) / 100
#     return f'The Simple Interest is: {interest}'


# Greet Hello
# @app.route('/hello', methods=['POST'])
# def hello():
#     name = str(request.form['name'])
#     return f'Hello {name}'


# Fahrenheit to Celsius
# @app.route('/fahrenheittocelsius', methods=['POST'])
# def fahrenheittocelsius():
#     fahrenheit = float(request.form['fahrenheit'])
#     celsius = (fahrenheit - 32) * 5/9
#     return f'The Temp in Celsius is: {celsius}'


# Miles to KMs
# @app.route('/milestokms', methods=['POST'])
# def milestokms():
#     miles = float(request.form['miles'])
#     kilometers = miles * 1.61
#     return f'The Dist in KMs is: {kilometers}'


if __name__ == '__main__':
    app.run(debug=True, port=5500)