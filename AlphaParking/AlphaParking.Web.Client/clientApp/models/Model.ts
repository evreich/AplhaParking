export default abstract class Model {
    toJSON() {
        return JSON.stringify(this);
    }
}