import * as React from 'react';

import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import { parseJwt } from '../utils/jwtUtils';
import ErrorNotification from './common/ErrorNotification';
import PageContent from './common/PageContent';
import PageNavBar from './common/PageNavBar';
import Router from './routes/Router';

class BaseComponent extends React.Component<IMapStateToProps & RouteComponentProps> {

    render() {
        const { isAuth: isAuthenticated, roles } = this.props;

        return <>
            <PageNavBar isAuth={isAuthenticated} />
            <PageContent>
                <>
                    <ErrorNotification />
                    <Router isAuth={isAuthenticated} userRoles={roles} />
                </>
            </PageContent>
        </>;
    }}

interface IMapStateToProps {
    isAuth: boolean;
    roles: string[];
}

const mapStateToProps = (state: any): IMapStateToProps => {
    const jwt = parseJwt(state.user.jwtToken);
    return {
        isAuth: state.user.jwtToken ? true : false,
        roles: jwt ? jwt.roles : []
    };
};

export default withRouter(connect<IMapStateToProps>(mapStateToProps)(BaseComponent));