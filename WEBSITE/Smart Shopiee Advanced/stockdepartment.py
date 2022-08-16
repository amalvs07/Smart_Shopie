from flask import *
from database import *

stockdepartment=Blueprint('stockdepartment ',__name__)
@stockdepartment.route('/')

def stockdepartmenthome():
	data={}
	login_id=session['login_id']
	w="SELECT * FROM `departmentemployees` WHERE login_id='%s'"%(login_id)
	pq=select(w)
	data['employeename']=pq
	return render_template('stockdepartmenthome.html',data=data)

@stockdepartment.route('/stockdepartmentproducts',methods=['get','post']) 

def stockdepartmentproducts():
	data={}
	login_id=session['login_id']

	w="SELECT * FROM `departmentemployees` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		employeeid=row['employee_id']
		employeeid=int(employeeid)

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']

	else:
		action=None


	if action=='update':
		q="SELECT stores.*,stock.*,products.* FROM  `stores`,`stock`,`products` WHERE stores.store_id=stock.store_id and products.product_id=stock.product_id and stock_id='%s'"%(id)
		res=select(q)
		data['updateproduct']=res


	if 'updation' in request.form:
		stock=request.form['stock']
		# unit=request.form['unit']

		q="UPDATE `stock` SET `avaliable_quantity`='%s' WHERE stock_id='%s'"%(stock,id)
		update(q)
		flash('Updated successfully')
		return redirect(url_for("stockdepartment .stockdepartmentproducts"))


	q="SELECT stores.*,stock.*,departmentemployees.*,products.* FROM  `departmentemployees`,`stores`,`stock`,`products` WHERE stores.store_id=stock.store_id and stores.store_id=departmentemployees.store_id and products.product_id=stock.product_id and departmentemployees.employee_id='%s'"%(employeeid)
	res=select(q) 
	res=select(q)
	data['product']=res
	return render_template("stockdepartmentproducts.html",data=data)

@stockdepartment.route('/stockdepartmentsales',methods=['get','post'])

def stockdepartmentsales():
	data={}
	login_id=session['login_id']

	w="SELECT * FROM `departmentemployees` WHERE login_id='%s'"%(login_id)
	pq=select(w)

	for row in pq:
		employeeid=row['employee_id']
		employeeid=int(employeeid)

	# if 'action' in request.args:
	# 	action=request.args['action']
	# 	id=request.args['id']
	# 	q="UPDATE `orderstatus` SET `update_description`='Deliverd' WHERE status_id='%s'"%(id)
	# 	update(q)
	# 	flash('Updated successfully')
	# 	return redirect(url_for("stockdepartment .stockdepartmentsales"))

	q="SELECT stores.*,users.*,payment.*,salesmaster.*,products.*,departmentemployees.*,salesdetails.* FROM  `stores`,`payment`,`users`,`departmentemployees`,`salesmaster`,`products`,`salesdetails` WHERE salesmaster.user_id=users.user_id and stores.store_id=salesmaster.store_id and stores.store_id=departmentemployees.store_id and salesmaster.sm_id=payment.sm_id and salesdetails.sm_id=salesmaster.sm_id and salesdetails.product_id=products.product_id and departmentemployees.employee_id='%s'"%(employeeid)
	res=select(q) 
	data['sales']=res
	a="SELECT orderstatus.*,stores.*,salesmaster.*,departmentemployees.*,users.* FROM `stores`,`orderstatus`,`departmentemployees`,`salesmaster`,users WHERE orderstatus.sm_id=salesmaster.sm_id and orderstatus.employee_id=departmentemployees.employee_id and stores.store_id=salesmaster.store_id and salesmaster.user_id=users.user_id and departmentemployees.employee_id='%s'"%(employeeid)
	# res=select(q)
	# flag=select(a)
	# data['orderstatus']=flag
	return render_template("stockdepartmentsales.html",data=data)
 	 