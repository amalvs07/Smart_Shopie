from flask import *
from database import *
import uuid
import qrcode

store=Blueprint('store ',__name__)
@store.route('/')

def storehome():
	data={}
	login_id=session['login_id']
	w="SELECT * FROM `stores` WHERE login_id='%s'"%(login_id)
	pq=select(w)
	data['storename']=pq
	return render_template('storehome.html',data=data)

@store.route('/storeemployee',methods=['get','post']) 
def  storeemployee():
	data={}
	login_id=session['login_id']
	w="SELECT store_id FROM `stores` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		storeid=row['store_id']
		storeid=int(storeid)
	

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']

	else:
		action=None
	if action=='delete':
		q="UPDATE `login` SET status=0 WHERE login_id='%s'"%(id)
		update(q)
		flash('Cuurent Employee is Deleted')

	if action=='update':
		q="SELECT * FROM departmentemployees WHERE login_id='%s'"%(id)
		res=select(q)
		data['upemployee']=res


	if 'submits' in request.form:
		firstname=request.form['first_name']
		lastname=request.form['last_name']
		gender=request.form['gender']
		datebirth=request.form['dob']
		phone=request.form['phone']
		place=request.form['place']
		

		

		q="UPDATE `departmentemployees` SET `first_name_employee`='%s',`last_name_employee`='%s',`dob`='%s',`place`='%s',`phone`='%s',`gender`='%s' WHERE `login_id`='%s' "%(firstname,lastname,datebirth,place,phone,gender,id)
		update(q)
		flash('Updated successfully')
		return redirect(url_for("store .storeemployee"))



		
	if 'submit' in request.form:
		firstname=request.form['first_name']
		lastname=request.form['last_name']
		gender=request.form['gender']
		dateofbirth=request.form['dob']
		phone=request.form['phone']
		place=request.form['place']
		email=request.form['email']
		password=request.form['password']
		q="INSERT INTO `login` (`username`,`password`,`usertype`)VALUES ('%s','%s','employee')"%(email,password)
		id=insert(q)
		q="INSERT INTO `departmentemployees` (`store_id`,`login_id`,`first_name_employee`,`last_name_employee`,`place`,`phone`,`email`,`gender`,`dob`) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(storeid,id,firstname,lastname,place,phone,email,gender,dateofbirth)
		insert(q)
		flash('Employee Registration Successfully')
		return redirect(url_for("store .storeemployee"))
	q="SELECT stores.*, login.*,departmentemployees.* FROM `login` INNER join `departmentemployees` INNER join `stores` on login.login_id=departmentemployees.login_id and stores.store_id=departmentemployees.store_id WHERE login.status='1' and stores.store_id='%s'"%(storeid)
	res=select(q)
	data['employee']=res	
	return render_template('storeemployee.html',data=data)


@store.route('/storeproduct',methods=['get','post']) 
def  storeproduct():
	data={}
	login_id=session['login_id']
	w="SELECT store_id FROM `stores` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		storeid=row['store_id']
		storeid=int(storeid)
	w="SELECT * from products where store_id=(select store_id from stores where login_id='%s')"%(login_id)
	pq=select(w)
	for row in pq:
		storeid=row['store_id']
		storeid=int(storeid)

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']

	else:
		action=None
	if action=='delete':
		q="UPDATE `products` SET status=0 WHERE product_id='%s'"%(id)
		update(q)
		flash('Cuurent Employee is Deleted')

	if action=='update':
		q="SELECT  products.*,stock.* FROM `products`,`stock` WHERE `products`.product_id=`stock`.product_id and products.product_id='%s'"%(id)
		res=select(q)
		data['upproducts']=res

	# if action=='qrcode':
	# 	q="SELECT  * FROM `products` WHERE product_id='%s'"%(id)
	# 	res=select(q)
	# 	data['qrvalue']=res


	if 'submits' in request.form:
		
		productname=request.form['product_name']

		image=request.files['image']
		path='static/upload/'+str(uuid.uuid4())+image.filename
		image.save(path)
		description=request.form['description']
		amount=request.form['amount']
		stock=request.form['stock']
		# unit=request.form['unit']
		
		

		q="UPDATE `products` SET `product_name`='%s',`description`='%s',`image`='%s',`amount`='%s' WHERE product_id='%s' and store_id='%s'"%(productname,description,path,amount,id,storeid)
		update(q)
		you="UPDATE `stock` SET `avaliable_quantity`='%s'  WHERE product_id='%s' and store_id='%s'"%(stock,id,storeid)
		update(you)
		flash('Updated successfully')
		return redirect(url_for("store .storeproduct"))



		
	if 'submit' in request.form:
		productname=request.form['product_name']
		image=request.files['image']
		path='static/upload/'+str(uuid.uuid4())+image.filename
		image.save(path)
		description=request.form['description']
		amount=request.form['amount']
		stock=request.form['stock']
		unit=request.form['unit']


		q="INSERT INTO `products` (`product_name`,`description`,`image`,`amount`,`store_id`,`status`) VALUES ('%s','%s','/%s','%s','%s','%s')"%(productname,description,path,amount,storeid,1)
		pi=insert(q)
		gh="INSERT INTO `stock` (`product_id`,`avaliable_quantity`,`unit`,`store_id`) VALUES ('%s','%s','%s','%s')"%(pi,stock,unit,storeid)
		insert(gh)
		pathh="static/qrcode/" + str(uuid.uuid4()) + ".png"
		qr=qrcode.make(pi)
		qr.save(pathh)
		flash('Product Inserted Successfully')
		return redirect(url_for("store .storeproduct"))
	q=	"SELECT stores.*, products.*,stock.* FROM `products` INNER join `stores` INNER join `stock` on stores.store_id=products.store_id and stock.product_id=products.product_id and stock.store_id=stores.store_id and products.status='1' and stores.store_id='%s'"%(storeid)
	res=select(q)
	data['products']=res
	return render_template('storeproduct.html',data=data)


@store.route('/storerating') 
def  storerating():
	
	data={}
	login_id=session['login_id']
	w="SELECT store_id FROM `stores` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		storeid=row['store_id']
		storeid=int(storeid)
	q="SELECT stores.*,rating.*,users.* FROM `stores`,`rating`,`users` WHERE stores.store_id=rating.store_id and users.user_id=rating.user_id and stores.store_id='%s'"%(storeid)
	ans=select(q)
	data['rating']=ans
	return render_template('storerating.html',data=data)


@store.route('/storeoutofstock') 
def  storeoutofstock():
	data={}
	login_id=session['login_id']
	

	w="SELECT store_id FROM `stores` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		storeid=row['store_id']
		storeid=int(storeid)

	q="SELECT  products.*,stock.*,stores.* FROM `products` INNER join `stores` INNER join `stock` on stores.store_id=products.store_id and stock.product_id=products.product_id and stock.store_id=stores.store_id and stock.avaliable_quantity < 1 and stores.store_id='%s'"%(storeid)
	ans=select(q)
	data['outofstock']=ans
	return render_template('storeoutofstock.html',data=data)

@store.route('/storesalesreport',methods=['get','post']) 
def  storesalesreport():

	data={}

	login_id=session['login_id']
	

	w="SELECT store_id FROM `stores` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		storeid=row['store_id']
		storeid=int(storeid)


	if 'action' in request.args:
		action=request.args['action']
	else:
		action=None
	if action=='rod':
		data['rod']=" "
	if action=='rbd':
		data['rbd']=" "
	if 'rodsubmit' in request.form:
		roddate=request.form['roddate']
		q="SELECT `salesmaster`.*,`salesdetails`.*,`products`.*,`stores`.*,`users`.*  FROM `salesmaster`,`salesdetails`,`products`,`stores`,`users` WHERE `salesmaster`.sm_id=`salesdetails`.sm_id AND `users`.user_id=`salesmaster`.user_id AND`products`.product_id=`salesdetails`.product_id AND `stores`.store_id=`salesmaster`.store_id and salesmaster.sales_date='%s' and stores.store_id='%s'"%(roddate,storeid)
		res=select(q)
		data['rodattend']=res
	if 'rbdsubmit' in request.form:
		srbddate=request.form['srbddate']
		erbddate=request.form['erbddate']
		q="SELECT `salesmaster`.*,`salesdetails`.*,`products`.*,`stores`.*,`users`.*  FROM `salesmaster`,`salesdetails`,`products`,`stores`,`users` WHERE `salesmaster`.sm_id=`salesdetails`.sm_id AND `users`.user_id=`salesmaster`.user_id AND`products`.product_id=`salesdetails`.product_id AND `stores`.store_id=`salesmaster`.store_id and stores.store_id='%s' and salesmaster.sales_date between '%s' and '%s' "%(storeid,srbddate,erbddate)
		res=select(q)
		data['rbdattend']=res					


		
	q="SELECT `salesmaster`.*,`salesdetails`.*,`products`.*,`stores`.*,`users`.*  FROM `salesmaster`,`salesdetails`,`products`,`stores`,`users` WHERE `salesmaster`.sm_id=`salesdetails`.sm_id AND `users`.user_id=`salesmaster`.user_id AND`products`.product_id=`salesdetails`.product_id AND `stores`.store_id=`salesmaster`.store_id and stores.store_id='%s'"%(storeid) 
	res=select(q)
	data['sales']=res
	return render_template('storesalesreport.html',data=data)


@store.route('/storestockreport',methods=['get','post']) 
def  storestockreport():

	data={}

	login_id=session['login_id']
	

	w="SELECT store_id FROM `stores` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		storeid=row['store_id']
		storeid=int(storeid)
	if 'action' in request.args:
		action=request.args['action']
	else:
		action=None
	if action=='singledate':

		data['singledate']=" "
	if action=='doubledate':

		data['doubledate']=" "
	if 'singlesubmit' in request.form:

		onedate=request.form['singledatesubmit']

		q="SELECT *,SUM(`salesdetails`.quantity) AS SUM_Quantity FROM `salesmaster`  INNER JOIN `salesdetails` USING(`sm_id`) INNER JOIN `products` USING(`product_id`) INNER JOIN `stores` ON `salesmaster`.`store_id`=`stores`.`store_id` WHERE `salesmaster`.`store_id`='%s'  and salesmaster.sales_date='%s' "%(storeid,onedate)
		res=select(q)
		data['salesinglestockdate']=res

	if 'doublesubmit' in request.form:
		fromddate=request.form['fromddate']
		todate=request.form['toddate']
		q="SELECT *,SUM(`salesdetails`.quantity) AS SUM_Quantity FROM `salesmaster`  INNER JOIN `salesdetails` USING(`sm_id`) INNER JOIN `products` USING(`product_id`) INNER JOIN `stores` ON `salesmaster`.`store_id`=`stores`.`store_id` WHERE `salesmaster`.`store_id`='%s'  and salesmaster.sales_date between '%s' and '%s' "%(storeid,fromddate,todate)
		res=select(q)
		data['salesdoublestockdate']=res	


	q="SELECT *,SUM(`salesdetails`.quantity) AS SUM_Quantity FROM `salesmaster`  INNER JOIN `salesdetails` USING(`sm_id`) INNER JOIN `products` USING(`product_id`) INNER JOIN `stores` ON `salesmaster`.`store_id`=`stores`.`store_id` WHERE `salesmaster`.`store_id`='%s' GROUP BY `product_id` ORDER BY `product_id` DESC"%(storeid)
	ans=select(q)
	data['outofstock']=ans
	return render_template('storestockreport.html',data=data)

