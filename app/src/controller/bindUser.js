define(['angular'], function(angular){
    angular.module('bind-user-controller', []).controller('bindUserController', function($scope, $rootScope, $navigate, $ajax){
        $scope.data = {
            mobile: "",
            code: ""
        };
        $scope.code = "";
        $scope.tag = false;
        $scope.sendCode = function(){
            if($scope.tag){
                alert("请勿频繁发送短信");
                return false;
            }
            console.log("send sms code");
            //验证手机号
            if(!$scope.data.mobile || !(/^1(3|4|5|7|8)\d{9}$/.test($scope.data.mobile))) {
                alert("请输入正确的手机号");
                return false;
            }
            $scope.tag = true;
            var timeout = setTimeout(function(){
                $scope.tag = false;
            }, 30000);
            $ajax.get({
                url: "/login/getSMSCode",
                data: {
                    cellphone: $scope.data.mobile
                },
                success: function(d){
                    if(d.result){
                        alert("短信已成功发送");
                        $scope.code = d.data;
                    }else{
                        alert("发送短信失败");
                        clearTimeout(timeout);
                        $scope.tag = false;
                    }

                },
                error: function(e){
                    alert("发送短信出错");
                    clearTimeout(timeout);
                    $scope.tag = false;
                }
            })

        };
        $scope.bindUser = function(){
            console.log("bind user: ", $scope.data);
            if(!$scope.data.mobile || !(/^1(3|4|5|7|8)\d{9}$/.test($scope.data.mobile))) {
                alert("请输入正确的手机号");
                return false;
            }
            if(!$scope.data.code || $scope.data.code != $scope.code){
                alert("请输入正确的验证码");
                return false;
            }
            $ajax.get({
                url: "/api/wechat/bindWeixinAccount",
                data: {
                	openId: $rootScope.user.openid,
                    cellphone: $scope.data.mobile,
                    password: $scope.data.code
                },
                success: function(d){
                	$navigate.go("/pc");
                },
                error: function(e){
                    alert("认证用户手机号出错");
                    
                }
            })

        };
    });
});