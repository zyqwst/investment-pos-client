webpackJsonp([8],{"7Y7s":function(t,e){},WsNN:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("Xxa5"),r=a.n(n),l=a("exGp"),o=a.n(l),i=a("9HqN"),s={data:function(){return{keyword:"",applyData:{},currentPage:1,loading:!1}},watch:{currentPage:function(){this.fetchPending()},keyword:_.debounce(function(){this.fetchPending()},300)},computed:{tableHeight:function(){return window.innerHeight-160}},activated:function(){this.applyData.content&&this.fetchPending()},methods:{change:function(){this.$router.replace({path:"/distribution"})},init:function(){applyData={},currentPage=1},toPage:function(t){this.currentPage=t},fetchPending:function(){var t=this;return o()(r.a.mark(function e(){var a;return r.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,t.loading=!0,e.next=4,Object(i.f)(t.keyword,t.currentPage-1);case 4:a=e.sent,t.applyData=a.object;case 6:return e.prev=6,t.loading=!1,e.finish(6);case 9:case"end":return e.stop()}},e,t,[[0,,6,9]])}))()},clickRow:function(t){var e=this;this.$store.dispatch("setDistribution",t).then(function(){e.$router.push({path:"/notapply-send"})})}}},c={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"distribution-pending"},[a("el-row",{staticClass:"pt-3",attrs:{type:"flex",justify:"center"}},[a("el-col",{attrs:{span:16}},[a("sy-input",{attrs:{placeholder:"销售单号/会员卡号/会员手机号",prefix:"",clearable:"",placement:"bottom-start"},on:{prefix:t.fetchPending},model:{value:t.keyword,callback:function(e){t.keyword=e},expression:"keyword"}})],1),t._v(" "),a("el-col",{attrs:{offset:1,span:2}},[a("el-button",{attrs:{icon:"el-icon-sort",type:"primary"},nativeOn:{click:function(e){return e.preventDefault(),t.change(e)}}},[t._v("申请单配送")])],1)],1),t._v(" "),a("el-row",[a("el-col",{attrs:{span:24}},[a("el-table",{ref:"applyTable",staticStyle:{width:"100%"},attrs:{data:t.applyData.content,stripe:"","highlight-current-row":"",height:t.tableHeight}},[a("el-table-column",{attrs:{type:"expand"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-form",{staticClass:"table-expand",attrs:{"label-position":"left",inline:""}},[a("el-form-item",{attrs:{label:"销售单号:"}},[a("span",[t._v(t._s(e.row.outTradeNo))])]),t._v(" "),a("el-form-item",{attrs:{label:"销售日期:"}},[a("span",[t._v(t._s(e.row.saleDate))])]),t._v(" "),a("el-form-item",{attrs:{label:"收银员:"}},[a("span",[t._v(t._s(e.row.saleMan))])])],1)]}}])}),t._v(" "),a("el-table-column",{attrs:{prop:"saleDate",label:"销售日期"}}),t._v(" "),a("el-table-column",{attrs:{prop:"name",label:"客户"}}),t._v(" "),a("el-table-column",{attrs:{prop:"phone",label:"电话"}}),t._v(" "),a("el-table-column",{attrs:{prop:"goodsCode",label:"商品代码"}}),t._v(" "),a("el-table-column",{attrs:{prop:"goodsName",label:"商品名称"}}),t._v(" "),a("el-table-column",{attrs:{label:"可配送数量"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("\n                        "+t._s(e.row.balance-e.row.controlQty)+"\n                    ")]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"操作",width:150},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"primary",size:"small"},nativeOn:{click:function(a){a.preventDefault(),t.clickRow(e.row)}}},[t._v("\n                        预约\n                        ")])]}}])})],1)],1)],1),t._v(" "),a("el-row",{attrs:{type:"flex",justify:"center"}},[a("el-pagination",{staticClass:"mt-2",attrs:{background:"",layout:"total, prev, pager, next",pageCount:t.applyData.totalPages,total:t.applyData.totalElements,pageSize:t.applyData.size,currentPage:t.currentPage},on:{currentChange:t.toPage,"update:currentPage":function(e){t.currentPage=e}}})],1)],1)},staticRenderFns:[]};var u=a("VU/8")(s,c,!1,function(t){a("7Y7s"),a("mot3")},"data-v-990c17ba",null);e.default=u.exports},mot3:function(t,e){}});