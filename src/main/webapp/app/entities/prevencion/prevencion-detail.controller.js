(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PrevencionDetailController', PrevencionDetailController);

    PrevencionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Prevencion', 'Anexo_prevencion', 'Peticion'];

    function PrevencionDetailController($scope, $rootScope, $stateParams, previousState, entity, Prevencion, Anexo_prevencion, Peticion) {
        var vm = this;

        vm.prevencion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:prevencionUpdate', function(event, result) {
            vm.prevencion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
