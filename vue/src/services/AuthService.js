import axios from 'axios';

export default {

  /**
   * Logs in the user by sending a POST request to the '/login' endpoint with the provided user object.
   *
   * @param {object} user - The user object containing the login credentials.
   * @param {string} user.username - The username of the user.
   * @param {string} user.password - The password of the user.
   *
   * @return {Promise} - A promise that resolves to the response of the login request.
   */
  login(user) {
    return axios.post('/login', user)
  },

  /**
   * Register a user
   *
   * @param {Object} user - The user object containing user details
   * @param {string} user.username - The username of the user
   * @param {string} user.email - The email of the user
   * @param {string} user.password - The password of the user
   *
   * @return {Promise} - A promise that resolves with the registration status
   */
  register(user) {
    return axios.post('/register', user)
  }

}
