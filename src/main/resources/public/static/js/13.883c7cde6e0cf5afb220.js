webpackJsonp([13],{"1gRw":function(e,t){},JUPN:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=a("Xxa5"),i=a.n(s),n=a("exGp"),c=a.n(n),r=a("Dd8w"),o=a.n(r),u=a("YN6D"),l=a("lHQf"),d=a("FB77"),m=a("NYxO"),h=a("VvTn"),p=a("7L0k"),v=a("jYxq");function b(e){return v.a.get("/print/sale",{billno:e})}var f=a("khYN"),g={components:{Calculator:u.a,CashType:d.a,MemberBar:l.a},data:function(){return{cash:0,card:0,pos:0,change:0,loading:!1,input:null,commitDisabled:!1,distributeStatus:0,receivableStatus:0}},computed:o()({},Object(m.b)(["cartGoods","member","isVip","isScan","isProtocalVip","cartAmount","curUser","curSaleMan"]),{amount:function(){return this.cartAmount}}),deactivated:function(){this.$destroy()},mounted:function(){this.init()},methods:{cardChange:function(e){this.card=e},cashChange:function(e){this.cash=e},posChange:function(e){this.pos=e},changeChange:function(e){this.change=e},back:function(){this.$router.back()},init:function(){this.cash=0,this.card=0,this.pos=0;this.member.amount,this.member.backAmount},focus:function(e){this.input=e},confirm:function(){var e=this;return c()(i.a.mark(function t(){var a,s,n;return i.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(!(e.cash+e.card+e.pos<e.amount&&0==e.receivableStatus)){t.next=3;break}return e.$message({message:"支付金额不足",type:"warning"}),t.abrupt("return");case 3:return a=[],e.cartGoods.filter(function(e){a.push({goodsId:e.goodsId,price:e.price,count:e.count,gift:e.gift.packageDtlId||-1,priceId:e.priceId,priceType:e.priceType,promotionId:e.promotionId})}),s={amount:e.amount,cardAmount:e.card,cashAmount:Object(h.c)(e.cash,1==e.receivableStatus?0:e.change),posAmount:e.pos,changeAmount:1==e.receivableStatus?0:e.change,memberId:e.member.id,payCash:e.cash,salemanId:e.curSaleMan.id,creUserId:e.curUser.id,distributeStatus:e.distributeStatus,receivableStatus:e.receivableStatus,list:a},t.prev=6,e.commitDisabled=!0,console.info(s),t.next=11,Object(p.m)(s);case 11:n=t.sent,e.$store.dispatch("clearAll"),e.$store.dispatch("removeMember"),e.print(n.object),e.$router.back(),t.next=21;break;case 18:t.prev=18,t.t0=t.catch(6),console.info(t.t0.response);case 21:return t.prev=21,e.commitDisabled=!1,t.finish(21);case 24:case"end":return t.stop()}},t,e,[[6,18,21,24]])}))()},print:function(e){var t=this;return c()(i.a.mark(function a(){var s,n;return i.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return t.loading=!0,a.next=3,b(e);case 3:s=a.sent,n=Object(f.d)(s.object),t.loading=!1,n&&t.$message.error(n);case 7:case"end":return a.stop()}},a,t)}))()}}},C={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"settle"},[a("navbar",{on:{back:e.back}},[a("div",{staticClass:"title"},[e._v("收银台")])]),e._v(" "),e.member.id?a("member-bar",{attrs:{member:e.member}}):e._e(),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.isVip,expression:"isVip"}],staticClass:"protocal"},[a("div",{staticClass:"distribute"},[a("span",{staticClass:"name"},[e._v("是否配送")]),e._v(" "),a("el-switch",{attrs:{"active-value":"1","inactive-value":"0",width:60},model:{value:e.distributeStatus,callback:function(t){e.distributeStatus=t},expression:"distributeStatus"}})],1),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.isProtocalVip,expression:"isProtocalVip"}],staticClass:"receivable"},[a("span",{staticClass:"name"},[e._v("是否挂账")]),e._v(" "),a("el-switch",{attrs:{"active-value":"1","inactive-value":"0",width:60},model:{value:e.receivableStatus,callback:function(t){e.receivableStatus=t},expression:"receivableStatus"}})],1)]),e._v(" "),a("div",{staticClass:"container"},[a("cash-type",{attrs:{amount:e.amount,showCard:e.isVip&&e.isScan,disable:1==e.receivableStatus},on:{focus:e.focus,cashChange:e.cashChange,posChange:e.posChange,changeChange:e.changeChange,cardChange:e.cardChange}}),e._v(" "),a("calculator",{staticClass:"calc",attrs:{input:e.input,disabled:e.commitDisabled,loading:e.commitDisabled},on:{enter:e.confirm}})],1)],1)},staticRenderFns:[]};var S=a("VU/8")(g,C,!1,function(e){a("zc8S"),a("1gRw")},"data-v-33556608",null);t.default=S.exports},zc8S:function(e,t){}});