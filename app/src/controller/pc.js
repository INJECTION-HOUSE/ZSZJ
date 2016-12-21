define(['angular', 'menu'], function(angular){
    angular.module('pc-controller', ['menu']).controller('pcController', function($scope, $rootScope, $navigate, $ajax){
        $scope.text = "会员中心";

        $scope.memberCenterClick = function(target) {
            $navigate.go('/'+target);
        }

        $scope.init = function(){
            $scope.isValid = $rootScope.user.valid;
            $scope.myScore = $rootScope.user.myScore;
            $scope.myIncome = $rootScope.user.myIncome;
            $scope.userName = $rootScope.user.nickname;
            // 获取头像
            $ajax.get({
                url:"/personCenter/avatar",
                cache:false,
                success:function(data){
                    if(data.result==1){
                        $(".member_center_photo_img").attr("src","upload/"+data.avatarPath);
                    }else{
                        $(".member_center_photo_img").attr("src","./images/icons/member_center_wtx.png");
                    }
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
                            /*$("#applyCertificationBtn").bind("click", function () {
                                console.log("go personal or enterpriser certification...");
                                window.location.href="${pageContext.request.contextPath}/personCenter/goCertification"
                            });*/
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