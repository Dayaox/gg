(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('PresentacionDialogController', PresentacionDialogController);

    PresentacionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Presentacion', 'Evaluacion'];

    function PresentacionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Presentacion, Evaluacion) {
        var vm = this;

        vm.presentacion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluacions = Evaluacion.query({filter: 'presentacion-is-null'});
        $q.all([vm.presentacion.$promise, vm.evaluacions.$promise]).then(function() {
            if (!vm.presentacion.evaluacion || !vm.presentacion.evaluacion.id) {
                return $q.reject();
            }
            return Evaluacion.get({id : vm.presentacion.evaluacion.id}).$promise;
        }).then(function(evaluacion) {
            vm.evaluacions.push(evaluacion);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.presentacion.id !== null) {
                Presentacion.update(vm.presentacion, onSaveSuccess, onSaveError);
            } else {
                Presentacion.save(vm.presentacion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:presentacionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
