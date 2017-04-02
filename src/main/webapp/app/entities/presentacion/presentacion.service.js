(function() {
    'use strict';
    angular
        .module('opleSioeApp')
        .factory('Presentacion', Presentacion);

    Presentacion.$inject = ['$resource'];

    function Presentacion ($resource) {
        var resourceUrl =  'api/presentacions/:id';

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
