(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('ImprocedenteDetailController', ImprocedenteDetailController);

    ImprocedenteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Improcedente', 'Evaluacion'];

    function ImprocedenteDetailController($scope, $rootScope, $stateParams, previousState, entity, Improcedente, Evaluacion) {
        var vm = this;

        vm.improcedente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:improcedenteUpdate', function(event, result) {
            vm.improcedente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
