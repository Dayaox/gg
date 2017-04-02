(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PresentacionDetailController', PresentacionDetailController);

    PresentacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Presentacion', 'Evaluacion'];

    function PresentacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Presentacion, Evaluacion) {
        var vm = this;

        vm.presentacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:presentacionUpdate', function(event, result) {
            vm.presentacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
