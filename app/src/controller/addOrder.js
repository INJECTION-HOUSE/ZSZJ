define(['angular'], function(angular){
    angular.module('add-order-controller', []).controller('addOrderController', function($scope, $routeParams, $navigate, $ajax, $rootScope){
        $scope.data = {
            arriveTime: 0,
            balance: 0
        };

        $scope.init = function() {
            $(".select").mobiscroll().select({
                lang: "zh",
                theme: "mobiscroll",
                display: "bottom"
            });
//            var type = $routeParams.type || 1;
//            $(".problemCategory").val(type);

            $(".datetimepicker").mobiscroll().date({
                lang: "zh",
                theme: "mobiscroll",
                display: "bottom"
            });

            $(".radio").unbind("click").bind("click", function(e){
                $(".radio").removeClass("active");
                $(e.currentTarget).addClass("active");
                var arriveTime = $(e.currentTarget).attr("value");
                $scope.data.arriveTime = arriveTime;
            });

            $scope.getBalance();
        };

        $scope.getBalance = function(){
            $ajax.get({
                url: "/member/getBalance",
                data: {
                    id: $rootScope.user.id
                },
                success: function(d){
                    $scope.data.balance = d || 0;
                }
            });
        }

        $scope.addOrderCommit = function() {
            var taskTitle = $('.problemTitle').val();
            if(taskTitle == null || taskTitle.length == 0) {
                alert("请输入标题");
                return;
            }
            var cellphone = $('.problemPhone').val();
            if(cellphone == null || cellphone.length == 0) {
                alert("请输入手机号");
                return;
            }
            var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
            if(!myreg.test(cellphone)) {
                alert("请输入有效手机号");
                return;
            }
            var prepayFee = $('.problemMoney').val()
            if(prepayFee == null || prepayFee.length == 0) {
                alert("请输入金额");
                return;
            }
            if(prepayFee < 50) {
                alert("预约金额需要大于50");
                return;
            }
            var addressDetail = $('.problemAddress').val();
            if(addressDetail == null || addressDetail.length == 0) {
                alert("请输入详细地址");
                return;
            }
            var errorTime = $('.problemTime').val();
            if(errorTime == null || errorTime.length == 0) {
                alert("请输入故障发生时间");
                return;
            }

            //验证余额
            if($scope.data.balance < prepayFee){
                //充值
                $scope.charge(prepayFee - $scope.data.balance);
                return;
            }
            var pos = getUserPos($rootScope.user.address);
            var params = {
                taskTitle: taskTitle,
                type: $('.problemType').val(),
                category: $('.problemCategory').val(),
                taskDesc: $('.problem_desc').val(),
                cellphone: cellphone,
                prepayFee: prepayFee,
                addressDetail: addressDetail,
                province:pos.province,
                city: pos.city,
                county: pos.country,
                deadline: new Date($('.problemStopTime').val()).getTime(),
                errorTime: new Date(errorTime).getTime()
            };


            $ajax.post({
                url: "/personCenter/savetask",
                data: params,
                success: function(d){
                    $navigate.go('/pc');
                },
                error: function(e){
                    alert("发布任务失败");
                    console.log(e);
                }
            });
        };

        $scope.charge = function(cash){
            if(confirm("您的余额不足，确认充值" + cash + "元？")) {
                $ajax.get({
                    url: '/payment/appUnifiedorder',
                    data: {
                        cash: cash,
                        openId: $rootScope.user.openid
                    },
                    success: function(d){
                        console.log(d);
                        if(d.success){
                            WeixinJSBridge.invoke(
                                'getBrandWCPayRequest', {
                                    "appId" : d.appid,
                                    "timeStamp": Math.round(new Date().getTime() / 1000),
                                    "nonceStr" :  d.nonce_str,
                                    "package" :  "prepay_id=" + d.prepay_id,
                                    "signType" : "MD5",
                                    "paySign" : d.sign
                                },
                                function(res){
                                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                                        $ajax.post({
                                            url: '/member/addTotalCash',
                                            data: {
                                                id: $rootScope.user.id,
                                                cash: cash
                                            },
                                            success: function(){
                                                $scope.data.balance = $scope.data.balance + cash;
                                                alert("充值成功");
                                            }
                                        });
                                    }else if(res.err_msg == "get_brand_wcpay_request:fail"){
                                        $(".finish").text("充值失败");
                                    }else{
                                        $(".finish").text("充值已取消");
                                    }
                                }
                            );
                        }else{
                            alert("充值失败");
                        }
                    }
                });
            } else{
                return;
            }
        };

        function getUserPos(pos){
            var res = {country: "", province: "", city: ""};
            if(pos){
                var p = pos.split(" ");
                res.country = p[0] || "";
                res.province = p[1] || "";
                res.city = p[2] || "";
            }
            return res;
        };

        $scope.init();
    });
});