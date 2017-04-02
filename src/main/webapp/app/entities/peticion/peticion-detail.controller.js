(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PeticionDetailController', PeticionDetailController);

    PeticionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Peticion', 'Usuario', 'Peticionario', 'Anexo', 'Prevencion', 'Evaluacion'];

    function PeticionDetailController($scope, $rootScope, $stateParams, previousState, entity, Peticion, Usuario, Peticionario, Anexo, Prevencion, Evaluacion) {
        var vm = this;

        vm.peticion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:peticionUpdate', function(event, result) {
            vm.peticion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
