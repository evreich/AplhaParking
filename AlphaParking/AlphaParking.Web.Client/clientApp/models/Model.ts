export default abstract class Model {
    getJSON() {
        return JSON.stringify(this);
    }
}