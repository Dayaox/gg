(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('Anexo_prevencionDetailController', Anexo_prevencionDetailController);

    Anexo_prevencionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Anexo_prevencion', 'Prevencion'];

    function Anexo_prevencionDetailController($scope, $rootScope, $stateParams, previousState, entity, Anexo_prevencion, Prevencion) {
        var vm = this;

        vm.anexo_prevencion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('opleSioeApp:anexo_prevencionUpdate', function(event, result) {
            vm.anexo_prevencion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
