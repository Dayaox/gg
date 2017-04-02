(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('AnexoDetailController', AnexoDetailController);

    AnexoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Anexo', 'Peticion'];

    function AnexoDetailController($scope, $rootScope, $stateParams, previousState, entity, Anexo, Peticion) {
        var vm = this;

        vm.anexo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:anexoUpdate', function(event, result) {
            vm.anexo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
