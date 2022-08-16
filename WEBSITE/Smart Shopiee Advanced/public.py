from flask import *
from database import *

public =Blueprint('public ',__name__)

@public.route('/',methods=['post','get'])

def home():
	return render_template('home.html')


@public.route('/login',methods=['get','post'])
def login():
	
	if 'login' in request.form:
		username=request.form['username']
		password=request.form['password']
		q="SELECT * FROM `login` WHERE username='%s' AND password='%s'"%(username,password)
		res=select(q)
		
		if res:
			session['login_id']=res[0]['login_id']
			if res[0]['usertype']=='admin':
				return redirect(url_for("admin .adminhome"))
			elif res[0]['usertype']=='store':
				return redirect(url_for("store .storehome"))
			elif res[0]['usertype']=='employee':
				return redirect(url_for("stockdepartment .stockdepartmenthome"))
		else:
			flash("Incorrect Username And Password")		
	return render_template("login.html")

@public.route('/forgot',methods=['get','post'])
def forgot():
	if 'verify' in request.form:
		username=request.form['usernamee']
		q="SELECT * FROM `login` WHERE username='%s' "%(username)
		res=select(q)
		if res:
			session['forget_id']=res[0]['login_id']
			return redirect(url_for("public .setpassword"))
		flash("Wrong email")
	return render_template("forgot.html")


@public.route('/setpassword',methods=['get','post'])
def setpassword():
	if 'confirm' in request.form:
		login_id=session['forget_id']
		password=request.form['password']
		cpassword=request.form['cpassword']
		if password == cpassword:
			d="UPDATE `login` SET password='%s' WHERE login_id='%s' "%(password,login_id)
			res=update(d)
			flash("New Password Set Successfully")
			return redirect(url_for("public .login"))
		else:
			flash("Not Same password")


	return render_template("setpassword.html")

# @public.route('/registration',methods=['get','post']) 
# def registration():
# 	if 'submit' in request.form:
# 		fname=request.form['fname']
# 		lname=request.form['lname']
# 		phno=request.form['phno']
# 		place=request.form['place']
# 		house=request.form['house']
# 		pincode=request.form['pincode']
# 		latitude=request.form['latitude']
# 		longitude=request.form['longitude']
# 		email=request.form['mail']
# 		pwd=request.form['Password']
# 		q="INSERT INTO `login` (`username`,`password`,`usertype`)VALUES ('%s','%s','user')"%(email,pwd)
# 		id=insert(q)
# 		q="INSERT INTO `users` (`login_id`,`first_name`,`last_name`,`phone`,`email`,`latitude`,`longitude`,`house`,`place`,`pincode`) VALUES ('%s','%s','%s','%s','%s','%s','%s')"%(id,fname,lname,gen,phno,place,email)
# 		insert(q)
# 		return redirect(url_for("public.registration"))
# 	return render_template("registration.html")



