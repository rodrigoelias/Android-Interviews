## ClearScore Test

**...But why?**

Due to time constrains, I choose to break several SOLID principles and not to UnitTest.

I've started with an MVVM architecture in mind although, both the Repository and the RemoteDataSource classes do not expose an interface making it difficult to mock.

This absence of an interface impact in the ability to test the ViewModel as well, making the code coupled.

Since the Dependency Inversion principle is not followed, no *dependency injection* framework is used.

**Some Explanation**

Although the code do not contain all the best practices, I tried to maintain it functional.

The MainActivity observes the *ScoreInfoViewModel* for changes on both *CreditInfo* and *NetworkStatus*. 

While the *ScoreInfoViewModel* requests the data to the *Repository*, which abstract the underlying access to the *RemoteDataSource*.

The *RemoteDataSource* is responsible for fetching the data from the given endpoint.

**Components**

To handle lifecycle events, I choose to use the *LiveData* along with *ViewModel* architectural component.

*Retrofit* is used to fetch the data from the remote endpoint (parsed with *GsonConverter*)

The chart used was plain-pie: https://android-arsenal.com/details/1/3689
