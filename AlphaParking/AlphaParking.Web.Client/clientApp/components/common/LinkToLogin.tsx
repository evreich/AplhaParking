import * as React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';

interface IProps extends RouteComponentProps {
    text: string;
}

const LinkToLogin: React.SFC<IProps> = (props) => {
    const { history: { push }, text } = props;
    return <a onClick={() => push('/login')}>{text}</a>;
} ;

export default withRouter(LinkToLogin);