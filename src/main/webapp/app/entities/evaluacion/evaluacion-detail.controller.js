(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('EvaluacionDetailController', EvaluacionDetailController);

    EvaluacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Evaluacion', 'Anexo_evaluacion', 'Procedente', 'Improcedente', 'Presentacion', 'Peticion'];

    function EvaluacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Evaluacion, Anexo_evaluacion, Procedente, Improcedente, Presentacion, Peticion) {
        var vm = this;

        vm.evaluacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:evaluacionUpdate', function(event, result) {
            vm.evaluacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
