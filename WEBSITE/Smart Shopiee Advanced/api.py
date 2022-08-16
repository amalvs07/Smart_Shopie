from flask import  *
import demjson
from database import *
import uuid
import os

def encode(data):
	return demjson.encode(data)

api = Blueprint('api',__name__)

@api.route('/user_details/', methods=['get','post'])
def user_details():
	data = {}
	username = request.args['login_id']
	q = "SELECT * from `users` where  login_id='%s'" %(username)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'user_details'
	return encode(data)

@api.route('/login/', methods=['get','post'])
def login():
	data = {}
	username = request.args['username']
	password = request.args['password']
	q = "SELECT * from login where username='%s' and password='%s' and status='1'" %(username, password)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	return encode(data)

@api.route('/register/', methods=['get','post'])
def register():
	data = {}
	first_name = request.args['first_name']
	last_name = request.args['last_name']
	house_name = request.args['house_name']
	place = request.args['place']
	pincode = request.args['pincode']
	phone = request.args['phone']
	email = request.args['email']
	latitude = request.args['latitude']
	longitude = request.args['longitude']
	password = request.args['password']

	log_qry = "INSERT into `login` (username, password,usertype,status) values ('%s', '%s', 'user','1')" %(email, password)
	log_id = insert(log_qry)

	reg_qry = "INSERT INTO `users`(`login_id`,`first_name`,`last_name`,`house`,`phone`,`email`,`place`,`latitude`,`longitude`,`pincode`) VALUES ((select max(login_id)from login),'" + first_name + "','" + last_name + "','" + house_name + "','" + phone + "','" + email + "','" + place + "','" + latitude + "','" + longitude + "','" + pincode + "')"
	user_id = insert(reg_qry)

	data['status'] = 'success'
	return encode(data)

@api.route('/scan_product/', methods=['get','post'])
def scan_product():
	data = {}
	qrid = request.args['qrid']
	q = "SELECT * FROM `products` INNER JOIN `stores` USING (store_id) INNER JOIN `login` USING(login_id) WHERE product_id = '%s' and `login`.status=1" %(qrid)

	res = select(q)
	if (res):
		data['status'] = 'success'
		data['data'] = res
	else:
		data['status'] = 'failed'
	data['method'] = 'scan_product'
	return encode(data)

# @api.route('/add_cart/',methods=['get','post'])
# def add_cart():
# 	data = {}
# 	pid = request.args['pid']
# 	logid = request.args['logid']
# 	qty = request.args['qty']
# 	qry = "INSERT INTO `cart` (`user_id`, `product_id`, `quantity`)values((SELECT user_id FROM users WHERE login_id = '%s'), '%s', '%s')" %(logid, pid, qty)
# 	insert(qry)

# 	data['status'] = 'success'
# 	data['method'] = 'add_cart'
# 	return encode(data)

# @api.route('/buy/',methods=['get','post'])
# def buy():
# 	data = {}
# 	logid = request.args['logid']
# 	qry = "SELECT * FROM `cart` INNER JOIN `products` USING(product_id) INNER JOIN `stock` USING(`product_id`) WHERE user_id=(select user_id from users where login_id='%s')" %(logid)
# 	res=select(qry)
# 	flag=0
# 	print(res)
# 	for row in res:
# 		print(flag)
# 		avail_qty = row['avaliable_quantity'];
# 		cart = row['cart_id'];
# 		prod_id = row['product_id'];
# 		store_id = row['store_id'];
# 		amount = row['amount'];
# 		qty = row['quantity'];
# 		iavail_qty = int(avail_qty) - int(qty);
# 		amnt = int(qty) * int(amount);

# 		if flag==0:
# 			q="INSERT INTO `salesmaster`(`user_id`,`total_amount`,`sales_date`,`store_id`)VALUES((select user_id from users where login_id='%s'),'0',curdate(),'%s')" %(logid,store_id)
# 			ids=insert(q)
# 			flag=1
# 		print(ids)
# 		print(flag)
# 		q="INSERT INTO `salesdetails`(`sm_id`,`product_id`,`quantity`,`amount`)values('%s','%s','%s','%s')" %(ids,prod_id,qty,amnt)
# 		insert(q)
# 		q="UPDATE `salesmaster` SET `total_amount` =(SELECT SUM(`amount`) FROM `salesdetails` WHERE `sm_id`='%s') WHERE `sm_id`='%s'" %(ids,ids)
# 		update(q)
# 		q="DELETE from cart where cart_id='%s'" %(cart)
# 		delete(q)
# 		q="UPDATE stock SET `avaliable_quantity`= '%s'WHERE `product_id`='%s'" %(iavail_qty,prod_id)
# 		update(q)
# 		q="SELECT * from orderstatus where sm_id='%s'" %(ids)
# 		res2=select(q)
# 		print(res2)
# 		if res2:
# 			pass
# 		else:
# 			q="INSERT INTO orderstatus VALUES(NULL,'%s','pending',CURDATE())" %(ids)
# 			insert(q)
# 	data['status'] = 'success'
# 	data['method'] = 'buy'
# 	return encode(data)

@api.route('/remove_cart/',methods=['get','post'])
def remove_cart():
	data = {}
	cartid = request.args['cartid']
	qry = "DELETE FROM `cart` WHERE `cart_id` = '%s'" %(cartid)
	delete(qry)

	data['status'] = 'success'
	data['method'] = 'remove_cart'
	return encode(data)

@api.route('/my_cart/', methods=['get','post'])
def my_cart():
	data = {}
	logid = request.args['logid']
	q = "SELECT * FROM `cart` INNER JOIN `products` USING (product_id) INNER JOIN `stores` USING (store_id) WHERE user_id = (SELECT user_id FROM users WHERE login_id = '%s')" %(logid)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'my_cart'
	return encode(data)

@api.route('/view_buy/', methods=['get','post'])
def view_buy():
	data = {}
	logid = request.args['logid']
	q = "SELECT * FROM `salesmaster` INNER JOIN `stores` USING (`store_id`)  WHERE `user_id` = (SELECT user_id FROM users WHERE login_id = '%s')" %(logid)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'view_buy'
	return encode(data)

@api.route('/store_view/', methods=['get','post'])
def store_view():
	data = {}
	q = "SELECT `stores`.*,`login`.* FROM `stores`,`login` WHERE `stores`.login_id=`login`.login_id and `login`.status=1"
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'store_view'
	return encode(data)

@api.route('/user_send_complaint/',methods=['get','post'])
def user_send_complaint():
	data = {}
	logid = request.args['logid']
	title = request.args['title']
	store_id = request.args['store_id']
	qry = "INSERT INTO `complaint`  VALUES (null,(SELECT user_id FROM users WHERE login_id = '%s'),'%s' , '%s', 'pending', curdate())" %(logid,store_id,title)
	insert(qry)

	data['status'] = 'success'
	data['method'] = 'user_send_complaint'
	return encode(data)

@api.route('/user_view_complaint/', methods=['get','post'])
def user_view_complaint():
	data = {}
	logid = request.args['logid']
	q = "SELECT * FROM `complaint` inner join `stores` using (store_id) WHERE  user_id = (SELECT user_id FROM `users` WHERE login_id = '%s')" %(logid)
	res = select(q)
	print(res)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'user_view_complaint'
	return encode(data)

@api.route('/nearby_stores/', methods=['get','post'])
def nearby_stores():
	data = {}
	latitude = request.args['latitude']
	longitude = request.args['longitude']
	q = "SELECT `stores`.*,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS( latitude ) ))) AS user_distance,`login`.* FROM `stores`,`login` WHERE `stores`.login_id=`login`.login_id and `login`.status=1 HAVING user_distance  < 31.068 ORDER BY user_distance ASC" %(latitude, longitude, latitude)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'nearby_stores'
	return encode(data)

# @api.route('/make_paymet/',methods=['get','post'])
# def make_paymet():
# 	data = {}
# 	sid = request.args['smid']
# 	amount = request.args['amount']
# 	qry = "INSERT INTO `payment` VALUES (null,'%s','%s','online',curdate())" %(sid,amount)
# 	insert(qry)
# 	q="UPDATE orderstatus set update_description='Paid' where sm_id='%s'" %(sid)
# 	update(q)
# 	# new imported
# 	query="UPDATE salesmaster set status='0' where sm_id='%s'" %(sid)
# 	update(query)
# 	data['status'] = 'success'
# 	data['method'] = 'make_paymet'
# 	return encode(data)

@api.route('/rate/',methods=['get','post'])
def rate():
	data = {}
	logid = request.args['logid']
	rating = request.args['rating']
	store_id = request.args['store_id']
	qry = "INSERT INTO `rating` VALUES (null,'%s',(SELECT user_id FROM users WHERE login_id = '%s'),'%s')" %(store_id,logid,rating)
	print(qry)
	insert(qry)

	data['status'] = 'success'
	return encode(data)

@api.route('/View_my_orders/', methods=['get','post'])
def View_my_orders():
	data = {}
	loginid = request.args['loginid']

	q = "SELECT * FROM `payment` INNER JOIN `salesmaster` USING(`sm_id`) INNER JOIN `salesdetails` USING(`sm_id`) INNER JOIN `products` USING(`product_id`) INNER JOIN `stores` ON `salesmaster`.`store_id`=`stores`.`store_id` WHERE `salesmaster`.`user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s') GROUP BY `sm_id` ORDER BY `sm_id` DESC"%(loginid)
	print(q)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'View_my_orders'
	return encode(data)

# @api.route('/Total_amount_view/', methods=['get','post'])
# def Total_amount_view():
# 	data = {}
# 	loginid = request.args['logid']

# 	q="SELECT * FROM `salesmaster` INNER JOIN `stores` USING (`store_id`)  INNER JOIN `salesdetails` USING (`sm_id`) INNER JOIN `products` USING (`product_id`) WHERE `sm_id`=(SELECT MaX(sm_id) FROM `salesmaster`) and `sd_id`=(SELECT Max(sd_id) From `salesdetails`) and `user_id` = (SELECT user_id FROM users WHERE login_id = '%s')"%(loginid)
# 	print(q)
# 	res = select(q)
# 	cq = "SELECT * FROM `cart` INNER JOIN `products` USING (product_id) WHERE user_id = (SELECT user_id FROM users WHERE login_id = '%s')" %(logid)
# 	print(cq)
# 	cart = select(cq)
# 	if (res):
# 		data['data'] = res
# 		data['cartdetails'] =cart
# 		data['status'] = 'success'
# 	else:
# 		data['status'] = 'failed'
# 	data['method'] = 'Total_amount_view'
# 	return encode(data)

# @api.route('/view_buy_new/', methods=['get','post'])
# def view_buy_new():
# 	data = {}
# 	logid = request.args['logid']
# 	q = "SELECT * FROM `salesmaster` INNER JOIN `stores` USING (`store_id`)  WHERE `user_id` = (SELECT user_id FROM users WHERE login_id = '%s')" %(logid)
# 	res = select(q)
# 	if (res):
# 		data['data'] = res
# 		data['status'] = 'success'
# 	else:
# 		data['status'] = 'failed'
# 	data['method'] = 'view_buy_new'
# 	return encode(data)

@api.route('/Forgot_password/', methods=['get','post'])
def Forgot_password():
	data = {}
	username = request.args['username']
	q = "SELECT * from login where username='%s' and status='1'" %(username)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']='Forgot_password'
	return encode(data)

@api.route('/Set_password/', methods=['get','post'])
def Set_password():
	data = {}
	loginid=request.args['login_id']
	password = request.args['password']
	d="UPDATE `login` SET password='%s' WHERE login_id='%s' "%(password,login_id)
	res=update(d)
	
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']='Set_password'
	return encode(data)




@api.route('/new_buy/',methods=['get','post'])
def new_buy():
	data = {}
	tamnt=0
	logid = request.args['logid']
	qry = "SELECT * FROM `cart` INNER JOIN `products` USING(product_id) INNER JOIN `stock` USING(`product_id`) WHERE user_id=(select user_id from users where login_id='%s')" %(logid)
	res=select(qry)
	flag=0
	print(res)

	for row in res:
		print(flag)
		avail_qty = row['avaliable_quantity'];
		cart = row['cart_id'];
		prod_id = row['product_id'];
		store_id = row['store_id'];
		amount = row['amount'];
		qty = row['quantity'];
		iavail_qty = int(avail_qty) - int(qty);
		amnt = int(qty) * int(amount);

		ttamnt= int(tamnt)+int(amnt);

		if flag==0:
			q="INSERT INTO `salesmaster`(`user_id`,`total_amount`,`sales_date`,`store_id`)VALUES((select user_id from users where login_id='%s'),'0',curdate(),'%s')" %(logid,store_id)
			ids=insert(q)
			flag=1
		print(ids)
		print(flag)
		q="INSERT INTO `salesdetails`(`sm_id`,`product_id`,`quantity`,`amount`)values('%s','%s','%s','%s')" %(ids,prod_id,qty,amnt)
		insert(q)
		q="UPDATE `salesmaster` SET `total_amount` =(SELECT SUM(`amount`) FROM `salesdetails` WHERE `sm_id`='%s') WHERE `sm_id`='%s'" %(ids,ids)
		update(q)
		q="DELETE from cart where cart_id='%s'" %(cart)
		delete(q)
		q="UPDATE stock SET `avaliable_quantity`= '%s'WHERE `product_id`='%s'" %(iavail_qty,prod_id)
		update(q)

		q="SELECT * from orderstatus where sm_id='%s'" %(ids)
		res2=select(q)
		print(res2)
		if res2:
			pass
		else:
			q="INSERT INTO orderstatus VALUES(NULL,'%s','paid',CURDATE())" %(ids)
			insert(q)
		pq="SELECT * FROM `payment` WHERE sm_id='%s'"%(ids)
		qp=select(pq)
		print(qp)
		if qp:
			pass
		else:
			qqq="INSERT INTO `payment` VALUES (null,'%s','%s','online',curdate())" %(ids,ttamnt)
			insert(qqq)
		rp="SELECT * FROM `salesmaster` WHERE sm_id='%s'"%(ids)
		you=select(rp)

	if (you):
		data['data'] = you
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method']='new_buy'
	return encode(data)

@api.route('/add_cart_new/',methods=['get','post'])
def add_cart_new():
	data = {}
	pid = request.args['pid']
	logid = request.args['logid']
	qty = request.args['qty']
	new="SELECT * FROM `cart` WHERE `product_id`='%s' and `user_id`=(SELECT user_id FROM users WHERE login_id = '%s')"%(pid,logid)
	get=select(new)
	if (get):
	    for i in get:
	    	avail_qty = i['quantity'];
	    sumqty=int(qty)+int(avail_qty)
	    updateval="UPDATE `cart` SET `quantity`='%s' WHERE `product_id`='%s' and `user_id`=(SELECT user_id FROM users WHERE login_id = '%s')"%(sumqty,pid,logid)
	    update(updateval)
	    data['status'] = 'success'
	else:
	    qry = "INSERT INTO `cart` (`user_id`, `product_id`, `quantity`)values((SELECT user_id FROM users WHERE login_id = '%s'), '%s', '%s')" %(logid, pid, qty)
	    insert(qry)
	    data['status'] = 'success'
	data['method'] = 'add_cart'
	return encode(data)


@api.route('/CREATE_PDF/', methods=['get','post'])
def CREATE_PDF():
	data = {}
	loginid = request.args['logid']
	smid = request.args['smid']
	q="SELECT `payment`.*,`salesmaster`.*,`salesdetails`.*,`salesdetails`.`amount` AS `productprice`,`products`.*,`stores`.*,`users`.* FROM `payment`,`salesmaster`,`salesdetails`,`products`,`stores`,`users` WHERE `payment`.`sm_id`=`salesmaster`.`sm_id`and `salesdetails`.`sm_id`=`salesmaster`.`sm_id` and `products`.`product_id`=`salesdetails`.`product_id` and`salesmaster`.`store_id`=`stores`.`store_id` and `salesmaster`.`user_id`=`users`.`user_id` and`salesmaster`.`user_id`=(SELECT `user_id` FROM `users` WHERE `login_id`='%s')   and  `salesmaster`.`sm_id`='%s'"%(loginid,smid)


	print(q)
	res = select(q)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'CREATE_PDF'
	return encode(data)

@api.route('/NEW_Scan_pro/', methods=['get','post'])
def NEW_Scan_pro():
	data = {}
	flag=0
	logid=request.args['logid']
	q = "SELECT * FROM `cart` INNER JOIN `products` USING (product_id) INNER JOIN `stores` USING (store_id) WHERE user_id = (SELECT user_id FROM users WHERE login_id = '%s')" %(logid)
	get=select(q)
	if (get):
		for x in get:
			idsss=x['store_id']
			if get[0]['store_id']==idsss:
				pass
			else:
				flag=1

		if (flag ==0):
			data['status']='success'
			data['data']=get
		if (flag ==1):
			data['status']='duplicate'
			data['data']=get
			
	# if (flag==0):
	# 	data['status'] = 'success'
	# 	data['data'] = get
	# elif (flag==1):
	# 	data['status']='duplicate'
	# 	data['data'] = get
	else:
		data['status'] = 'failed'
	data['method'] = 'NEW_Scan_pro'
	return encode(data)

@api.route('/Delete_all/',methods=['get','post'])
def Delete_all():
	data = {}
	logid = request.args['logid']
	qry = "DELETE FROM `cart` WHERE `user_id` = (SELECT user_id FROM users WHERE login_id = '%s')"%(logid)
	res = delete(qry)
	if (res):
		data['data'] = res
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'Delete_all'
	return encode(data)