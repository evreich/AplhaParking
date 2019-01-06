import * as React from 'react';

import { connect } from 'react-redux';
import '../stylesheets/common.scss';
import ErrorNotification from './common/ErrorNotification';
import PageNavBar from './common/PageNavBar';
import Router from './routes/Router';

interface IMapStateToProps {
    isAuthenticated: boolean;
}

const App: React.SFC<IMapStateToProps> = (props) => {
    return <>
        <PageNavBar isAuth={props.isAuthenticated}/>
        <Router isAuth={props.isAuthenticated} />
        <ErrorNotification />
    </>;
};

const mapStateToProps = (state: any): IMapStateToProps => {
    const { jwtToken } = state.user;
    // TODO: parse token and check role user
    return jwtToken ? { isAuthenticated: true } : { isAuthenticated: false };
};

export default connect<IMapStateToProps>(mapStateToProps)(App);