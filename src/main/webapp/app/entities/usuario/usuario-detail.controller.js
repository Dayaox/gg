(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('UsuarioDetailController', UsuarioDetailController);

    UsuarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Usuario', 'Peticion'];

    function UsuarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Usuario, Peticion) {
        var vm = this;

        vm.usuario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:usuarioUpdate', function(event, result) {
            vm.usuario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
