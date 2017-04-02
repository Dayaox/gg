(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('ProcedenteDetailController', ProcedenteDetailController);

    ProcedenteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Procedente', 'Evaluacion'];

    function ProcedenteDetailController($scope, $rootScope, $stateParams, previousState, entity, Procedente, Evaluacion) {
        var vm = this;

        vm.procedente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:procedenteUpdate', function(event, result) {
            vm.procedente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
