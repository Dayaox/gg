(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('AnexoDialogController', AnexoDialogController);

    AnexoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Anexo', 'Peticion'];

    function AnexoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Anexo, Peticion) {
        var vm = this;

        vm.anexo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.peticions = Peticion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.anexo.id !== null) {
                Anexo.update(vm.anexo, onSaveSuccess, onSaveError);
            } else {
                Anexo.save(vm.anexo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:anexoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
