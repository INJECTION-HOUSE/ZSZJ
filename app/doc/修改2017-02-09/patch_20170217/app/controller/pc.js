define(['angular', 'menu'], function(angular){
    angular.module('pc-controller', ['menu']).controller('pcController', function($scope, $rootScope, $navigate, $ajax){
        $scope.text = "会员中心";

        $scope.memberCenterClick = function(target) {
            if(target == 'addOrder' && !$scope.user.isValid){
                alert("您尚未绑定手机号，请进行绑定。");
                return;
            }
            $navigate.go('/'+target);
        }
        $scope.user = {
            isValid: false,
            myScore: 0,
            myIncome: 0,
            userName: $rootScope.user.nickname || "",
            headimgurl : $rootScope.user.headimgurl || ""
        };

        $scope.init = function(){
            $ajax.get({
                url:"/api/wechat/requestUserInfo",
                cache:false,
                data: {
                    openId: $rootScope.user.openid
                },
                success:function(data){
                    $scope.user.isValid = data.valid ? true : false;
                    $scope.user.myIncome = data.income ? data.income : 0;
                    $scope.user.myScore = data.score ? data.score : 0;
                }
            });
            // 获取用户身份
            $ajax.get({
                url:"/api/wechat/queryRole",
                cache:false,
                success:function(data){
                    if(data.result==1){
                        $("#repairtaskId").hide();
                        if(data.role == 'unknown') {
                            $(".member_center_person_type").html("未认证");
//                            $(".member_center_person_type").unbind("click").bind("click", function(e){
//                                $navigate.go('/bindUser');
//                            });
                        }
                        if(data.role == 'processing') {
                            $(".member_center_person_type").html("认证处理中");
                        }
                        if(data.role == 'member') {
                            $(".member_center_person_type").html("个人会员");
                        }
                        if(data.role == 'enterprise') {
                            $(".member_center_person_type").html("企业会员");
                            $("#repairtaskId").show();
                        }
                    }else{
                        alert(data.result);
                    }
                }
            });
        };
        $scope.init();
    });
});