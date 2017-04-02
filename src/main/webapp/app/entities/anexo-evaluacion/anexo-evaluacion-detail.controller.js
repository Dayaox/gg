(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('Anexo_evaluacionDetailController', Anexo_evaluacionDetailController);

    Anexo_evaluacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Anexo_evaluacion', 'Evaluacion'];

    function Anexo_evaluacionDetailController($scope, $rootScope, $stateParams, previousState, entity, Anexo_evaluacion, Evaluacion) {
        var vm = this;

        vm.anexo_evaluacion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:anexo_evaluacionUpdate', function(event, result) {
            vm.anexo_evaluacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
