import * as React from 'react';

import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from 'react-router-dom';
import ErrorNotification from './common/ErrorNotification';
import PageContent from './common/PageContent';
import PageNavBar from './common/PageNavBar';
import Router from './routes/Router';

class BaseComponent extends React.Component<IMapStateToProps & RouteComponentProps> {

    render() {
        const { isAuth: isAuthenticated } = this.props;

        return <>
            <PageNavBar isAuth={isAuthenticated} />
            <PageContent>
                <>
                    <ErrorNotification />
                    <Router isAuth={isAuthenticated} />
                </>
            </PageContent>
        </>;
    }}

interface IMapStateToProps {
    isAuth: boolean;
}

const mapStateToProps = (state: any): IMapStateToProps => {
    return {
        isAuth: state.user.jwtToken ? true : false
    };
};

export default withRouter(connect<IMapStateToProps>(mapStateToProps)(BaseComponent));