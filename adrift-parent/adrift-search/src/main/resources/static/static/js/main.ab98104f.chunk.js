(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{122:function(e,t,n){e.exports=n(327)},327:function(e,t,n){"use strict";n.r(t);var a={};n.r(a),n.d(a,"showRoutings",function(){return h});var r=n(0),l=n.n(r),i=n(42),o=n.n(i),u=n(7),c=n(15),p=n(110),m=n(111),s=n(329),d=n(12),f=Object(d.fromJS)({routings:[],size:0}),h=function(e,t){return{type:"search/SHOW_ROUTINGS",routings:e,size:t}},g=Object(m.combineReducers)({form:s.a,search:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:f,t=arguments.length>1?arguments[1]:void 0;return"search/SHOW_ROUTINGS"===t.type?e.set("routings",e.get("routings").merge(t.routings)).set("size",e.get("size")+t.size):e}}),E=window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__||c.compose,v=Object(c.createStore)(g,E(Object(c.applyMiddleware)(p.a))),b=n(113),x=n(114),O=n(119),w=n(115),y=n(120),S=n(10),j=n(8),N=n(9);function I(){var e=Object(j.a)(["\n\theight:30px;\n"]);return I=function(){return e},e}function k(){var e=Object(j.a)(["\n\tbackground: #eff0f1;\n\ttd{\n\t\twidth:120px;\n\t}\n\n"]);return k=function(){return e},e}function z(){var e=Object(j.a)(["\n\theight: 40px;\n\tbackground: red;\n\tth{\n\t\twidth:120px;\n\t}\n"]);return z=function(){return e},e}function F(){var e=Object(j.a)(["\n\tposition: relative;\n\twidth: 150px;\n\tcursor: pointer;\n\tborder-radius: 10px;\n\tbackground: #0093d2;\n\ttext-align: center;\n\tfont-size: 25px;\n"]);return F=function(){return e},e}function _(){var e=Object(j.a)(["\n\tmargin-bottom: 15px;\n\tspan{\n\t\tmargin-left: -20px;\n\t\twidth:25px;\n\t\theight:20px;\n\t}\n\t.textInput {\n\t\twidth: 160px;\n\t\theight: 20px;\n\t\tmargin-left: 10px;\n\t\tfont-size: 14px;\n\t\tborder-radius: 7px;\n\n\t}\n\t.textTitle{\n\t\twidth:auto;\n\t\theight:auto;\n\t}\t\n\n"]);return _=function(){return e},e}function T(){var e=Object(j.a)(["\n    overflow: hidden;\n\twidth: 90%;\n\tmargin: 0 auto;\n\n"]);return T=function(){return e},e}function C(){var e=Object(j.a)(["\n\tposition: relative;\n\tmargin-top: -20px;\n\theight: 50px;\n"]);return C=function(){return e},e}function D(){var e=Object(j.a)(["\n\tposition: relative;\n\tfloat: right;\n\tpadding-top: 30px;\n\twidth: 76%;\n\toutline:1px solid #eee;\n\tbackground:none;\n\tul.li.span{\n\t\tdisplay: block;\n\t\twidth: 200px;\n\t\ttext-align: left;\n\n\t}\n"]);return D=function(){return e},e}function J(){var e=Object(j.a)(["\n\tposition: fixed;\n\tfloat: left;\n\tmargin-left: 15px;\n\tpadding-top: 30px;\n\ttext-align: center;\n\twidth: 20%;\n\tbackground: #eff0f1;\n\n"]);return J=function(){return e},e}var R=N.a.div(J()),L=N.a.div(D()),M=N.a.div(C()),P=N.a.div(T()),U=N.a.div(_()),W=N.a.button(F()),G=(N.a.thead(z()),N.a.tbody(k()),N.a.div(I()),Object(S.reduxForm)({form:"searchForm"})(function(e){var t=e.handleSubmit;e.pristine,e.reset,e.submitting;return l.a.createElement("form",{onSubmit:t},l.a.createElement(U,null,l.a.createElement("span",{style:{marginLeft:"30px"}},"\u5355\u7a0b:"),l.a.createElement(S.Field,{name:"tripType",component:"input",type:"radio",value:"1"}),l.a.createElement("span",{style:{marginLeft:"30px"}},"\u5f80\u8fd4:"),l.a.createElement(S.Field,{name:"tripType",component:"input",type:"radio",value:"2"})),l.a.createElement(U,null,l.a.createElement("span",null,"\u51fa\u53d1\u57ce\u5e02:"),l.a.createElement(S.Field,{name:"depCity",component:"input",type:"text",placeholder:"depCity",className:"textInput"})),l.a.createElement(U,null,l.a.createElement("span",null,"\u5230\u8fbe\u57ce\u5e02:"),l.a.createElement(S.Field,{name:"arrCity",component:"input",type:"text",placeholder:"arrCity",className:"textInput"})),l.a.createElement(U,null,l.a.createElement("span",null,"\u53bb\u7a0b\u65e5\u671f:"),l.a.createElement(S.Field,{name:"depDate",component:"input",type:"text",placeholder:"depDate",className:"textInput"})),l.a.createElement(U,null,l.a.createElement("span",null,"\u8fd4\u7a0b\u65e5\u671f:"),l.a.createElement(S.Field,{name:"retDate",component:"input",type:"text",placeholder:"retDate",className:"textInput"})),l.a.createElement(U,null,l.a.createElement("span",null,"\u6210\u4eba\u4eba\u6570:"),l.a.createElement(S.Field,{name:"adtNumber",component:"select",className:"textInput"},l.a.createElement("option",{value:"1"},"1"),l.a.createElement("option",{value:"2"},"2"),l.a.createElement("option",{value:"3"},"3"),l.a.createElement("option",{value:"4"},"4"),l.a.createElement("option",{value:"5"},"5"),l.a.createElement("option",{value:"6"},"6"),l.a.createElement("option",{value:"7"},"7"),l.a.createElement("option",{value:"8"},"8"),l.a.createElement("option",{value:"9"},"9"))),l.a.createElement(U,null,l.a.createElement("span",null,"\u513f\u7ae5\u4eba\u6570:"),l.a.createElement(S.Field,{name:"chdNumber",component:"select",className:"textInput"},l.a.createElement("option",{value:"0"},"0"),l.a.createElement("option",{value:"1"},"1"),l.a.createElement("option",{value:"2"},"2"),l.a.createElement("option",{value:"3"},"3"),l.a.createElement("option",{value:"4"},"4"),l.a.createElement("option",{value:"5"},"5"))),l.a.createElement(W,null,"\u641c\u7d22"))})),H=n(118),X=n.n(H),B=function(e){function t(){return Object(b.a)(this,t),Object(O.a)(this,Object(w.a)(t).apply(this,arguments))}return Object(y.a)(t,e),Object(x.a)(t,[{key:"render",value:function(){var e=this.props,t=e.handleSubmit,n=e.routings,a=e.size,r=n.toJS();return l.a.createElement(P,null,l.a.createElement(R,null,l.a.createElement(G,{onSubmit:t})),l.a.createElement(L,null,l.a.createElement(M,null,l.a.createElement("label",null,"\u62a5\u4ef7\u6761\u6570:"),a),l.a.createElement("ul",null,r.map(function(e,t){var n=e.fromSegments.segments;if(e.retSegments)e.retSegments.segments;var a="";return n.map(function(e,t){e.flightNumber&&(a+=e.flightNumber+"_")}),l.a.createElement("li",{key:e.data},l.a.createElement("span",null,e.validatingCarrier)," ",l.a.createElement("span",null,a)," ",l.a.createElement("span",null,e.adultTotalPrice))}))))}}]),t}(l.a.Component),V=new WebSocket("ws://localhost:9688/websocket");V.onMessage=function(e){console.log(e)};var q=Object(u.connect)(function(e){return{routings:e.getIn(["search","routings"]),size:e.getIn(["search","size"])}},function(e){return{handleSubmit:function(t){var n=t;0===n.size&&(n={}),n.adtNumber||(n.adtNumber=1),n.chdNumber||(n.chdNumber=0);var r=JSON.stringify(n),l={code:1e4};l.extension=r,V.send(JSON.stringify(l)),X.a.post("/hello/search",n).then(function(t){for(var n=[],r=t.data.routings,l=0,i=r.length-1;i>=0;i--)n.push(r[i]),i%100===0&&function(){var t=n;n=[],setTimeout(function(){e(a.showRoutings(t,t.length))},1e3*l),l++}()}).catch(function(e){console.log(e)})}}})(B),A=function(){return l.a.createElement(u.Provider,{store:v},l.a.createElement(q,null))};o.a.render(l.a.createElement(A,null),document.getElementById("root"))}},[[122,2,1]]]);
//# sourceMappingURL=main.ab98104f.chunk.js.map