webpackJsonp([9],{A3oJ:function(t,e){},"T+/8":function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n("mvHQ"),s=n.n(o),r=n("62Bg"),a=n("NC6I"),i=n.n(a),l=n("khYN"),c={name:"login",components:{SizeControl:r.a},data:function(){return{loginForm:{username:"",password:""},loginRules:{username:[{required:!0,trigger:"blur",validator:function(t,e,n){e.length<2?n(new Error("用户名不可为空")):n()}}],password:[{required:!0,trigger:"blur",validator:function(t,e,n){e.length<1?n(new Error("密码不可为空")):n()}}]},loading:!1,passwordType:"password"}},methods:{showPwd:function(){"password"===this.passwordType?this.passwordType="":this.passwordType="password"},handleLogin:function(){var t=this;this.$refs.loginForm.validate(function(e){if(!e)return console.log("参数异常"),!1;t.loading=!0;var n=JSON.parse(s()(t.loginForm));n.password=i()(n.password).toUpperCase(),n.computerId=Object(l.a)(),t.$store.dispatch("login",n).then(function(e){t.$router.replace({path:"/"})}).catch(function(e){t.loading=!1})})},maximize:function(){JSCall.maximize()},minimize:function(){JSCall.minimize()}},created:function(){},destroyed:function(){}},p={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"login-container"},[n("div",{staticClass:"top-right"},[n("size-control")],1),t._v(" "),n("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{autoComplete:"on",model:t.loginForm,rules:t.loginRules,"label-position":"left"}},[n("div",{staticClass:"title-container"},[n("h3",{staticClass:"title"},[t._v("丹的收银")])]),t._v(" "),n("el-form-item",{attrs:{prop:"username"}},[n("span",{staticClass:"svg-container svg-container_login"},[n("svg-icon",{attrs:{"icon-class":"user"}})],1),t._v(" "),n("sy-input",{attrs:{name:"username",placement:"right",type:"text",placeholder:"用户名"},model:{value:t.loginForm.username,callback:function(e){t.$set(t.loginForm,"username",e)},expression:"loginForm.username"}})],1),t._v(" "),n("el-form-item",{attrs:{prop:"password"}},[n("span",{staticClass:"svg-container"},[n("svg-icon",{attrs:{"icon-class":"password"}})],1),t._v(" "),n("sy-input",{attrs:{name:"password",type:t.passwordType,autoComplete:"on",placeholder:"密码"},nativeOn:{keyup:function(e){return"button"in e||!t._k(e.keyCode,"enter",13,e.key,"Enter")?t.handleLogin(e):null}},model:{value:t.loginForm.password,callback:function(e){t.$set(t.loginForm,"password",e)},expression:"loginForm.password"}}),t._v(" "),n("span",{staticClass:"show-pwd",on:{click:t.showPwd}},[n("svg-icon",{attrs:{"icon-class":""==t.passwordType?"open-eye":"eye"}})],1)],1),t._v(" "),n("el-button",{staticStyle:{width:"100%"},attrs:{type:"primary",loading:t.loading},nativeOn:{click:function(e){return e.preventDefault(),t.handleLogin(e)}}},[t._v("登录")]),t._v(" "),n("router-link",{staticStyle:{width:"100%",color:"#889aa4","margin-top":"1rem"},attrs:{tag:"div",to:"/regist"}},[n("div",{staticStyle:{float:"right","text-decoration":"underline"}},[t._v("门店注册")])])],1)],1)},staticRenderFns:[]};var d=n("VU/8")(c,p,!1,function(t){n("vGnl"),n("A3oJ")},"data-v-7c80ffb9",null);e.default=d.exports},vGnl:function(t,e){}});