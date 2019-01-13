import * as React from 'react';

import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import store from '../store';
import '../stylesheets/common.scss';
import BaseComponent from './BaseComponent';

const App: React.SFC = (props) => {
    return (
        <Provider store={store}>
            <BrowserRouter>
                <BaseComponent />
            </BrowserRouter>
        </Provider>);
};

export default App;