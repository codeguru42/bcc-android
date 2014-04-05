(Un)conventional Android

My name is Richard Cirerol. You may remember me from previous code camp and user group presentations. I have presented on HTTP, Git, NServiceBus, FluentNHibernate, NancyFx and other various open-source .NET frameworks.

I currently work for an awesome company out of Point Richmond, California: Vertigo Software. We are a consulting company that specializes in assisting media companies with the development of their web, desktop, console, and mobile applications.

We have many high profile clients, including HBO, NBC Universal, and CBS.

I started with Vertigo last year and had no Android experience. (My previous experience has been in web development and before that server and network operations.)

In my trial by fire, I have learned that there are many ways to do Android wrong, but more importantly I have learned how to apply software engineering practices to make Android application development easier to do right.

# The problem

I am going to take the first few minutes to set the context for this session. Don't worry - we will get to the code soon!

To get the most out of this session, I expect that, at minimum, you will have some nominal experience with writing Android applications. If you are an iOS or Windows phone developer, you may be able to draw some analogies.

As we develop our applications, we face two synergistic forces: complexity and entropy. If these forces are not managed, our applications suffer from something called "Technical Debt." This happens with nearly all application development, save perhaps very simple applications. Sometimes we consciously and deliberately create debt. Other times debt increases in a stealthy manner. And crippling debt tends to only to show its ugliness at the most inopportune time...when a deadline is looming.

Let's take a look at these two forces.

The first is complexity.

Complexity is inevitable. Sometimes we allow the complexity in, and sometimes it creeps in unnoticed. But complexity comes with the territory. If we mismanage complexity, we allow the other force, entropy, into our system.

Entropy is a gradual decline into disorder. Evidence of entropy can be identified in applications while looking at the source code and while running the application. 

As a user of an application, when might you see entropy?
	* an application loses your preferences
	* when a scroll is choppy
	* when you get ANRs, or application not responding, messages
	* when an application crashes. 

As a developer, you'll see entropy too. What are some signs of entropy in your applications?
	* when a class or method is so long you have difficulty absorbing its purpose and function
	* when you can't remember or discover if the application has a specific algorithm

Entropy has an unmatched ability to coerce us into adding additional complexity into our application. I know you've encountered this. 

How have you allowed entropy into your applications? There is no judgment here. We've all done it. This is a safe environment. :)
	* you add yet another "else if" block or "case" block to a method 
	* you write the same data or API access code for a third, fourth, fifth time and haven
	* or worse, you realize you have code that is inefficient and complex. (That's not bad!) You write a more efficient and elegant implementation. (Great job!) You leave the old way in because its so entrenched in your code it would take more effort than you're willing to invest to re-implement.

And as complexity increases, entropy increases, and these two forces fan each others flames if not put in check.

# The solution

So, what do we do? 

First, we make conscious efforts to develop our application with sensible defaults, or conventions. I am not talking about convention over configuration specifically. I am talking about being consistent and predictable. Naming things in a consistent fashion. Organizing classes and resources in consistent ways. 

Second, we turn to our software engineering principles. You may know some of these principles by their acronyms. The two I will touch on in this talk are SOLID and DRY.

(Define principles)

# Frameworks

Throughout this session, you will see me using several open-source frameworks. Most have been open-sourced by a company named Square, Inc (or its employees).

# List Adapter Patterns

I'm going to play a little clip for you, cuz I'm like that.

## The Inflate and Find Pattern (adapter_pt_1)

Here we are causing a performance hit. Inflating each row every time is wasteful. We need to recycle, just like in real life.

Let's go ahead and refactor this a little. 

Ok, now we are reusing our row view when we can. But, we are having to find our textview elements on every pass.

Is there a better way? (of course`)

## The ViewHolder Pattern (adapter_pt_2)

Here is a pattern you will see quite a bit in the wild. The idea is to take a class and store it with the view. The class has references to the view elements (already inflated) to just update with new information.

Why its better?

* Rows are recycled (view inflation only happens if necessary)

Why its not?

* Still very cluttered and unreadable 
* What is the purpose of this method? ... To get the view - not to construct it. This method is still doing too much (S in SOLID)

Is there more we can do?

## The ButterKnife Holder Pattern (adapter_pt_3)

We have imported a View Injection Library from the insanely smart Jake Wharton who now works for Square. 

In this pattern, we pass our view into the view holder and instruct Butterknife to find and assign our annotated fields.

Why is this better?

* We are using a dependency injection (D in SOLID anyone) to create our view dependencies. This still only happens once per "visible" row and the holder gets recycled.

Can we make it better? (of course)

* We can also pass in the model to the view holder and have it assign the values to the TextViews.

Is this the best we can do? (of course...wait...I mean...NO!)

* Is the method still doing more than it should?
* I think so

Let's make one more refinement

## The ExternalListItem View Pattern (adapter_pt_4)

Here is the final step...or what we like to call the right way

Our get view has 3 steps:

* create or recycle a view (model)
* pass the model data to the view model
* return the configured view as a row in the list

Our SessionListItemView is subclassed from RelativeLayout and does what our ViewHolder did. But this is more explicit. And the adapter is much cleaner.

If I've done this right, I never have to open this adapter again. It does its job. This is the O in SOLID.


# Async Operations

Assuming all of you have worked with Android before, how many of you create connected applications?

What is the rule about getting data over a network connection?

You can't perform network operations on the main thread of the application. You must move that operation to a separate thread.

This is smart on Google's part. (Wait - those people at Google are smart?)

What are our options for running tasks off the main thread?

We can:

* create our own thread
* use an async task
* use an Intent service
* use a library

I don't think that I would recommend creating your own thread very often, especially in regards to UI network operations. So, we'll just skip right to the async task.

## The Async Task
Let's take a look at our SessionListFragment

In our scenario, we need data from our endpoint so we can display our list of sessions.

So, in my onResume() method, I check to see if I have an existing list adapter, and if not, I execute my background task.

Notice, I do this in onResume rather than onCreate. This is so I can handle returning from the detail page.

This application uses a view pager. The view pager loads the current fragment and the fragments adjacent to them. So, our network call is happening as soon as we launch the application.

Pretty slick, task runs - WHA-BAM! - I get my session list.

Are there any problems here?

What happens if you leave the activity before the network call completes?

Let's find out.  We'll make our network call really slow.

Then as soon as we launch our application, we'll send it to the background (causing our MainActivity to be destroyed).

Cool our app is running in the background - everything seems to be working fine. Oh no! Crashed.

Let's figure out what is happening. 

Looking at the stack trace we can see that the activity was null when the postexecute block was run. So, even though we destroyed the activity itself. The async task still ran to completion ... sort-of.

The first thing we should do is guard against a null activity.

(update code)

So, now we don't crash when we background the activity.

Unfortunately, the async task still runs to completion even when we background the activity.

To prevent that, we need to cancel the task if we background the _fragment_ not just the activity. If this were a more intense task, we would really want to save some memory and clock cycles. 

If this were an async task that returned progress, we would probably want to have it stop processing any existing callbacks. And just to be safe we can add a guard in the post execute block too.

# Using retrofit

I wanted to show you a couple of http async libraries.

The first is retrofit from Square. Now, we were using the synchronous retrofit calls when we had our async tasks. This time we will use the built-in callbacks.

Unfortunately, we still need to guard against the activity being null. And there is no way to cancel the call if the fragment is paused, but that is in the works for v2.0.

# Using volley

Volley is very similar to Retrofit. It was introduced at Google IO last year.

With volley, you create a RequestQueue singleton for the app. When you need to send an async web request, you create a Volley request and add it to the queue.

The request has a method, url, return type and success and error listeners very similar to Retrofit. Unlike retrofit, each request formula has to be created separately.

Retrofit and Volley perform very similar tasks. Retrofit is slightly faster and has a more conventional request syntax. Volley has the advantage of single and aggregate request cancellation.

If I were to advise between the two, I would say that Retrofit is better for well-defined HTTP APIs, whereas Volley works better than retrofit for one-off requests or where cancellation is a priority.

# Using an Intent Service

We have a problem here, principling speaking. We are violating the open/closed principle. If we decide at some point in the future that we want to get the data from the database or a different api, we have to open this class and change the implementation. We probably have to open up another class as well.

It would behoove us to abstract that call out of the activity and to some other entity.

We can do that with an intent service.

An intent service takes a requests, runs in a background thread OFF the main thread.  When it is done, we can notify changes via a broadcast receiver or a content provider.

# Using a Service with an Event Bus

Android has a primitive event bus built in. We saw the broadcast receiver which works within the app and can be used to work outside the app. I can listen to events from other applications.

There is a local broadcast receiver that is available for in-app broadcasts only.  Unfortunately, this construct is very verbose and requires a lot of busywork using Intents and strings (hopefully from constants) and a lot of casting and unboxing.

I have started using a library put out by Square, Inc.




