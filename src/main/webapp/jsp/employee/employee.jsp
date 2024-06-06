<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee Portal</title>
<link rel="stylesheet"
	href=<%=request.getContextPath()+"/css/style.css"%>>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
	<div class="page">
		<div class="top-nav">
			<button class="top-nav-button get-btn" id="dashboard">
				<span id="home-icon" class="material-symbols-outlined"> home
				</span>
			</button>
			<button class="top-nav-button get-btn" id="profile">
				<span id="profile-icon" class="material-symbols-outlined">
					account_circle </span>
			</button>
			<a href="/Continental/app/logout">
				<button class="top-nav-button" id="logout">
					<span id="logout-icon" class="material-symbols-outlined">logout </span>
				</button>
			</a>
		</div>
		<div class="main">
			<div class="side-nav">
				<div class="accounts side-nav-part">
					<button class="side-nav-part-toggler" data-target="accounts-opt">
						ACCOUNTS</button>
					<div class="accounts-opt side-nav-part-opt hidden">
						<button class="sub-btn get-btn" id="employee/add-account">ADD
							ACCOUNT</button>
						<button class="sub-btn get-btn" id="employee/view-account">VIEW
							ACCOUNT</button>
					</div>
				</div>
				<div class="customers side-nav-part">
					<button class="side-nav-part-toggler" data-target="customers-opt">
						CUSTOMERS</button>
					<div class="customers-opt side-nav-part-opt hidden">
						<button class="sub-btn get-btn" id="employee/add-customer">ADD
							CUSTOMER</button>
						<button class="sub-btn get-btn" id="employee/view-customer">VIEW
							CUSTOMER</button>
					</div>
				</div>
				<div class="transactions side-nav-part">
					<button class="side-nav-part-toggler"
						data-target="transactions-opt">TRANSACTIONS</button>
					<div class="transactions-opt side-nav-part-opt hidden">
						<button class="sub-btn get-btn" id="employee/deposit">DEPOSIT</button>
						<button class="sub-btn get-btn" id="employee/withdraw">WITHDRAW</button>
						<button class="sub-btn get-btn" id="employee/transfer">TRANSFER</button>
					</div>
				</div>
				<button class="non-toggled get-btn" id="employee/view-statement">
					STATEMENTS</button>
			</div>
			<div class="content">
				<%
				Object currentPage = request.getSession().getAttribute("currentPage");
				%>
				<%
				if (currentPage != null) {
				%>
				<script>
				window.onload=()=>{
					document.getElementById('<%=currentPage%>').click();
					console.log(document.getElementById('<%=currentPage%>'));
				}
				</script>
				<%
				} else {
				%>
				<%@ include file="employee-dash.jsp"%>
				<%
				}
				%>
			</div>
		</div>
	</div>
</body>
<script
  src="https://code.jquery.com/jquery-3.7.1.js"
  integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
  crossorigin="anonymous"></script>
<script src=<%=request.getContextPath()+"/js/script.js"%>></script>
</html>