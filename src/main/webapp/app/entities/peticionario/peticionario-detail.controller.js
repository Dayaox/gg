(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PeticionarioDetailController', PeticionarioDetailController);

    PeticionarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Peticionario', 'Peticion'];

    function PeticionarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Peticionario, Peticion) {
        var vm = this;

        vm.peticionario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:peticionarioUpdate', function(event, result) {
            vm.peticionario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
