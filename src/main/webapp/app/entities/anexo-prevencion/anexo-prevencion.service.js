(function() {
    'use strict';
    angular
        .module('opleSioeApp')
        .factory('Anexo_prevencion', Anexo_prevencion);

    Anexo_prevencion.$inject = ['$resource'];

    function Anexo_prevencion ($resource) {
        var resourceUrl =  'api/anexo-prevencions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
