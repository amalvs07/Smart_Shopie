from flask import *
from database import *

admin=Blueprint('admin ',__name__)
@admin.route('/')

def adminhome():
	return render_template('adminhome.html')



@admin.route('/adminmanagestores',methods=['get','post']) 
def adminmanagestores():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']

	else:
		action=None
	if action=='delete':
		# q="delete from stores where login_id='%s'"%(id)
		# delete(q)
		# q1="delete from login where login_id='%s'"%(id)
		# delete(q1)
		q="UPDATE `login` SET status=0 WHERE login_id='%s'"%(id)
		update(q)
		flash('Cuurent Store is Deleted')

	if action=='update':
		q="SELECT * FROM stores WHERE login_id='%s'"%(id)
		res=select(q)
		data['upstore']=res


	if 'submits' in request.form:
		storename=request.form['store_name']
		phone=request.form['phone']
		logitude=request.form['logitude']
		latitude=request.form['latitude']
		

		

		q="UPDATE `stores` SET `store_name`='%s',`phone`='%s',`latitude`='%s',`longitude`='%s' WHERE login_id='%s'"%(storename,phone,latitude,logitude,id)
		update(q)
		flash('Updated successfully')
		return redirect(url_for("admin .adminmanagestores"))



		
	if 'submit' in request.form:
		storename=request.form['store_name']
		phone=request.form['phone']
		logitude=request.form['logitude']
		latitude=request.form['latitude']
		email=request.form['email']
		password=request.form['password']
		q="INSERT INTO `login` (`username`,`password`,`usertype`)VALUES ('%s','%s','store')"%(email,password)
		id=insert(q)
		q="INSERT INTO `stores` (`login_id`,`store_name`,`phone`,`email`,`latitude`,`longitude`) VALUES ('%s','%s','%s','%s','%s','%s')"%(id,storename,phone,email,latitude,logitude)
		insert(q)
		flash('Store Registration Successfully')
		return redirect(url_for("admin .adminmanagestores"))
	q=	"SELECT stores.*, login.* FROM `login` INNER join `stores` on login.login_id=stores.login_id WHERE login.status='1'"
	res=select(q)
	data['store']=res	
	return render_template("adminmanagestores.html",data=data)



@admin.route('/adminviewusers')

def adminviewusers():
	data={}
	q="SELECT * FROM `users`" 
	res=select(q)
	data['users']=res
	return render_template("adminviewusers.html",data=data)

@admin.route('/adminviewsales')

def adminviewsales():
	data={}
	q="SELECT stores.store_id AS shop_id, first_name, last_name, product_name,image, store_name, quantity, `salesdetails`.`amount` AS qty_amount FROM salesdetails INNER JOIN salesmaster USING (sm_id) INNER JOIN users USING(user_id) INNER JOIN products USING (product_id) INNER JOIN stores ON stores.`store_id` = products.`store_id`"
	res=select(q)
	data['sales']=res

	return render_template('adminviewsales.html',data=data)

@admin.route('/adminviewcomplaints',methods=['get','post'])

def adminviewcomplaints():
	data={}
	q="SELECT `complaint`.*,`users`.*,`stores`.*,`stores`.phone AS store_phone,`users`.phone AS user_phone FROM `complaint`,`users`,`stores` WHERE `users`.user_id=`complaint`.user_id AND `stores`.store_id=`complaint`.store_id" 
	res=select(q)
	data['complaint']=res

	j=0
	for i in range(1,len(res)+1):
		if 'rply'+str(i) in request.form:
			reply=request.form['reply'+str(i)]
			q="UPDATE `complaint` SET reply_description='%s' WHERE complaint_id='%s'"%(reply,res[j]['complaint_id'])
			update(q)
			flash('Reply send Successfully')

			return redirect(url_for("admin .adminviewcomplaints"))
		j=j+1


	return render_template('adminviewcomplaints.html',data=data)