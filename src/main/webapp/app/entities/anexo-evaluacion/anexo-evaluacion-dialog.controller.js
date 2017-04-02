(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('Anexo_evaluacionDialogController', Anexo_evaluacionDialogController);

    Anexo_evaluacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Anexo_evaluacion', 'Evaluacion'];

    function Anexo_evaluacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Anexo_evaluacion, Evaluacion) {
        var vm = this;

        vm.anexo_evaluacion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluacions = Evaluacion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.anexo_evaluacion.id !== null) {
                Anexo_evaluacion.update(vm.anexo_evaluacion, onSaveSuccess, onSaveError);
            } else {
                Anexo_evaluacion.save(vm.anexo_evaluacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:anexo_evaluacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
