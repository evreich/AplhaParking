"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var React = require("react");
var constants_1 = require("../../constants");
require("../../stylesheets/common.scss");
var HelloComponent = /** @class */ (function (_super) {
    __extends(HelloComponent, _super);
    function HelloComponent(props) {
        var _this = _super.call(this, props) || this;
        _this.state = {
            cars: []
        };
        return _this;
    }
    HelloComponent.prototype.componentDidMount = function () {
        var _this = this;
        var conf = {
            method: 'GET',
            mode: 'cors'
        };
        fetch(constants_1.default.SERVER_API + "/car", conf)
            .then(function (response) { return response.ok && response.json(); })
            .then(function (result) {
            return _this.setState({
                cars: result
            });
        })
            .catch(function (err) { return err || 'Пиздец на сервере \_(O_o)_/'; });
    };
    HelloComponent.prototype.render = function () {
        return React.createElement("div", { className: 'main' },
            "\u041F\u043E\u043B\u0443\u0447\u0435\u043D\u043D\u044B\u0435 \u043C\u0430\u0448\u0438\u043D\u044B:",
            this.state.cars.map(function (elem) {
                return React.createElement("span", { style: { color: '#00FF00', display: 'block' } },
                    " ",
                    Object.entries(elem).reduce(function (accum, currVal) { return accum + currVal + ' '; }, ''));
            }));
    };
    return HelloComponent;
}(React.Component));
exports.default = HelloComponent;
//# sourceMappingURL=MainLayout.js.map