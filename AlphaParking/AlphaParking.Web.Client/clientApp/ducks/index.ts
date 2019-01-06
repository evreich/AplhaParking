import { combineReducers } from 'redux';
import request from './request';
import user from './user';

const reducer = combineReducers({
  request,
  user
});

export default reducer;