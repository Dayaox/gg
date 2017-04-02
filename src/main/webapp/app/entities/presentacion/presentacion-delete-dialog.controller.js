(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PresentacionDeleteController',PresentacionDeleteController);

    PresentacionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Presentacion'];

    function PresentacionDeleteController($uibModalInstance, entity, Presentacion) {
        var vm = this;

        vm.presentacion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Presentacion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
