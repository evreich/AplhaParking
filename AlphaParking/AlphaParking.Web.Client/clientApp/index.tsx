import * as React from 'react';
import * as ReactDOM from 'react-dom';

import { Provider } from 'react-redux';

import App from './components/App';

import './stylesheets/common.scss';

declare let module: any;

const rootElem = document.getElementById('react-root');
ReactDOM.render(
    <App />,
    rootElem
);

if (module.hot)
    module.hot.accept('./components/App', () => {
        const NextApp = require('./components/App').default;
        ReactDOM.render(<NextApp />, rootElem);
    });