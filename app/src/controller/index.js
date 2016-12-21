define(['angular', 'loading'], function(angular){
    angular.module('index-controller', ['loading']).controller('indexController', function($scope, $ajax, $rootScope, $navigate){

//        if(navigator.geolocation){
//            navigator.geolocation.getCurrentPosition(function(position){
//                console.log(position);
////                var lat = position.coords.latitude;
////                var lng = position.coords.longitude;
//            }, function(e){
//                console.log(e);
//            });
//        }else{
//            alert("您的浏览器不支持自动定位!");
//        }

//        $ajax.get({
//            url: "/login/signin",
//            data: {
//                userAccount: "15262452610",
//                password: "123456"
//            },
//            success: function(d){
////                console.log("login result: ", d);
//                $rootScope.user = {
//                    id: 2,
//                    memeberId: 7,
//                    nickname: 'kaku',           // 姓名
//                    gender: 0,                  // 性别
//                    birthday: '1992-10-10',     // 生日
//                    telephone: '15262452610',   // 手机号
//                    qqNumber: '12121221',       // QQ号
//                    workAddress: '苏州市吴中区',  // 通讯地址
//                    currentAddress: '苏州',      // 城市
//                    occupy: '职业信息',          // 职业信息
//                    introdDesc: '个人介绍',       // 个人介绍
//                    skills: '擅长技能',           // skills
//                    myScore: 10,                // 我的积分
//                    myIncome: 200,               // 我的收益
//                    isValid: false
//                };
//            },
//            error: function(e){
//                console.log("login error: ", e)
//            }
//        });
        var code = $navigate.param("code") || "021wxCFE0Pyomg2ZYQHE0CJWFE0wxCFQ";
        var state = $navigate.param("state") || "ZHUSUZHIJIA-987ERIA12345";
        $ajax.get({
            url: "/api/wechat/requestAccessCode",
            data: {
                code: code,
                state: state
            },
            success: function(d){
                if(d.errcode == 0 && d.data){
                    $rootScope.user = d.data;
                }else{
                    alert("登陆失败");
                }
            },
            error: function(e){
                console.log("login error: ", e);
                alert("登陆出错");
            }
        });
    });
});