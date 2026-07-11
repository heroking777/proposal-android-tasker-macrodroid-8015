import requests

class AndroidManagementAPIClient:
    def __init__(self, api_key):
        self.api_key = api_key
        self.base_url = "https://androidmanagement.googleapis.com/v1"

    def _make_request(self, method, endpoint, params=None, data=None):
        headers = {
            'Authorization': f'Bearer {self.api_key}',
            'Content-Type': 'application/json'
        }
        url = f"{self.base_url}{endpoint}"
        response = requests.request(method, url, headers=headers, json=data, params=params)
        response.raise_for_status()
        return response.json()

    def list_enterprises(self):
        endpoint = "/enterprises"
        return self._make_request("GET", endpoint)

    def get_enterprise(self, enterprise_id):
        endpoint = f"/enterprises/{enterprise_id}"
        return self._make_request("GET", endpoint)

    def create_web_app(self, enterprise_id, web_app_data):
        endpoint = f"/enterprises/{enterprise_id}/webApps"
        return self._make_request("POST", endpoint, data=web_app_data)

    def list_web_apps(self, enterprise_id):
        endpoint = f"/enterprises/{enterprise_id}/webApps"
        return self._make_request("GET", endpoint)

    def get_web_app(self, enterprise_id, web_app_id):
        endpoint = f"/enterprises/{enterprise_id}/webApps/{web_app_id}"
        return self._make_request("GET", endpoint)

# Example usage:
# api_client = AndroidManagementAPIClient('YOUR_API_KEY')
# enterprises = api_client.list_enterprises()
# print(enterprises)
```

This is a basic implementation of an Android Management API client in Python. You can extend this client with additional methods to cover more endpoints as needed for your specific use case, such as managing policies, devices, or users within the enterprise context.