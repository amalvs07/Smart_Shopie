from flask import Flask
from public import public
from admin import admin
from api import api
from store import store
from database import database 
from stockdepartment import stockdepartment

app=Flask(__name__)
app.secret_key="Reference"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(api,url_prefix='/api')
app.register_blueprint(store,url_prefix='/store')
app.register_blueprint(stockdepartment,url_prefix='/stockdepartment')

app.run(debug=True,port=5555,host="192.168.42.128")
# 192.168.43.243

